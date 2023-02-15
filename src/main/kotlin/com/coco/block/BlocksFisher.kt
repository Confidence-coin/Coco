package com.coco.block

import com.coco.snap.SnapBuilder
import com.coco.snap.Snaps

object BlocksFisher {

    /**
     * Starts fishing for new blocks in the NPFS sea
     */
    fun goFishing() {
        while (true) {
            collectBlocks()
            createASnap()
            voteAndCreateABlock()
        }
    }

    @Suppress("UNREACHABLE_CODE")
    private fun voteAndCreateABlock() {
        val nextBlocks: Array<BlockData> = TODO()

        val blockData: BlockData = BlockBuilder.build(nextBlocks)
        publishBlock(blockData)
    }

    private fun publishBlock(blockData: BlockData) {
        TODO("Not yet implemented")
    }

    @Suppress("UNREACHABLE_CODE")
    private fun createASnap() {
        val blocks: Array<BlockData> = TODO()
        val nextBlocks: Array<BlockData> = TODO()

        val snap = SnapBuilder.build(blocks, nextBlocks, Snaps.snap)
        Snaps.updateSnap(snap)
    }

    private fun collectBlocks() {
        TODO("It will send new blocks to onNewBlock")
    }
}