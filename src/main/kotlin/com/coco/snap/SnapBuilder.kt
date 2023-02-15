package com.coco.snap

import com.coco.api.ApiObject
import com.coco.block.BlockData
import com.coco.block.OPTING
import com.coco.block.UPDATE_TRANSACTION
import com.coco.block.Vote
import java.io.Serializable
import java.util.*

object SnapBuilder {

    fun build(
        blocks: Array<BlockData>,
        unfilteredNextBlocks: Array<BlockData>,
        currentSnap: Snap
    ): Snap {

        val minRequiredVotes = currentSnap.validators.size * (2f / 3f)

        val commonSnapCid = findMostCommonSnapCid(unfilteredNextBlocks)
        if (currentSnap.snapCid != commonSnapCid) {
            throw Error("wrong local snapCid")
        }

        // remove blocks with the wrong CID
        val nextBlocks = unfilteredNextBlocks.filter { it.baseSnap != currentSnap.snapCid }.toTypedArray()
        val totalVoters = nextBlocks.size
        if (totalVoters < minRequiredVotes) {
            throw Error("No consensus!")
        }

        val nextSnap = NextSnap(currentSnap)

        val votingMap = calculateVotes(nextBlocks)

        blocks.sort() // Not sure if this is needed but normalizing the data as much as possible is a good idea

        for (block in blocks) {
            var id = 0
            for (topic in block.topics) {
                for (item in topic.items) {
                    if (totalVoters - (votingMap[block.walletId]?.get(id) ?: 0) > minRequiredVotes) {
                        addToSnap(nextSnap, topic.topicId, item)
                    }
                    id++
                }
            }
        }

        return nextSnap.build()
    }

    private fun findMostCommonSnapCid(blockDataList: Array<BlockData>): String {
        val snapCidFrequencyMap = mutableMapOf<String, Int>()

        var mostCommonSnapCid = ""
        var maxFrequency = 0

        for (blockData in blockDataList) {
            val snapCid = blockData.baseSnap
            val frequency = snapCidFrequencyMap.getOrDefault(snapCid, 0) + 1
            if (frequency > maxFrequency) {
                mostCommonSnapCid = snapCid
                maxFrequency = frequency
            }
            snapCidFrequencyMap[snapCid] = frequency
        }


        return mostCommonSnapCid
    }

    /**
     * @return a map of rejections where the key is the Validator id and the value is the rejections map
     * as returned from [computeRejectedItemsMap]
     */
    private fun calculateVotes(nextBlocks: Array<BlockData>): TreeMap<Int, TreeMap<Int, Int>> {
        val votingMap = TreeMap<Int, TreeMap<Int, Int>>()

        for (block in nextBlocks) {
            for (vote in block.votes) {
                votingMap.compute(vote.walletId) { _: Int, value: TreeMap<Int, Int>? ->
                    computeRejectedItemsMap(value, vote)
                }
            }
        }
        return votingMap
    }

    /**
     * @return a map of rejected items where the key is the item index in the block and value is the rejections count
     */
    private fun computeRejectedItemsMap(
        value: TreeMap<Int, Int>?,
        vote: Vote
    ): TreeMap<Int, Int> {
        val result = value ?: TreeMap<Int, Int>()
        for (item in vote.rejectedItems) {
            result.compute(item) { _, rejectedItem ->
                (rejectedItem ?: 0) + 1
            }
        }

        return result
    }

    private fun addToSnap(nextSnap: NextSnap, topicId: Byte, item: ApiObject) {
        when (topicId) {
            UPDATE_TRANSACTION -> addUpdateTransaction(nextSnap, item.toUpdateTransaction())
            OPTING -> TODO()
        }
    }
}

private data class VoteData(val walletId: Int, val rejections: Array<Int>)


class NextSnap(snap: Snap) : Serializable {
    fun build(): Snap {
        TODO("Not yet implemented")
    }

    val snapId = snap.snapId + 1
    val validators = snap.validators.associateBy {
        it.walletId
    }
    val dTps = snap.dTps.map { dtp ->
        MutableDtp().apply {
            lastSnapId = dtp.lastSnapId
            members = dtp.members.toMutableList()
            walletAddress = dtp.walletAddress
        }
    }.associateBy {
        it.walletAddress
    }
}