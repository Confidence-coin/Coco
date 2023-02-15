package com.coco.block

import com.coco.snap.Snaps
import com.coco.snap.rejectUpdateTransaction
import java.util.*

object BlockBuilder {
    val rejections = TreeMap<String, MutableList<Int>>()

    @Suppress("UNREACHABLE_CODE")
    fun build(nextBlocks: Array<BlockData>): BlockData {
        nextBlocks.sort() // normalize

        val topics = mutableListOf<Topic>() // TODO: implement this
        val votes = mutableListOf<Vote>()


        for (nextBlock in nextBlocks) {
            val rejectedItems = mutableListOf<Int>()
            var id = 0
            for (topic in nextBlock.topics) {
                for (item in topic.items) {
                    when (topic.topicId) {
                        UPDATE_TRANSACTION -> if (rejectUpdateTransaction(item)) rejectedItems += id
                    }
                    id++
                }
            }
            votes.add(Vote(nextBlock.cId, nextBlock.walletId, rejectedItems.toTypedArray()))
        }


        val unsignedBlockData = UnsignedBlockData(
            Snaps.snap.snapCid,
            Snaps.snap.snapId + 1,
            TODO(),
            System.currentTimeMillis(),
            topics.toTypedArray(),
            votes.toTypedArray(),
            TODO()
        )

        return signAndPublish(unsignedBlockData)
    }

    private fun signAndPublish(unsignedBlockData: UnsignedBlockData): BlockData {
        TODO("Not yet implemented")
    }


}


