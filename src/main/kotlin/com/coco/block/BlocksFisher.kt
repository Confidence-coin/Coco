package com.coco.block

import com.coco.api.ApiObject
import com.coco.signature.Signatures
import com.coco.snap.Snaps

object BlocksFisher {

    /**
     * Starts fishing for new blocks in the NPFS sea
     */
    fun start() {
        TODO()
    }

    fun onNewBlock(block: BlockData) {
        for (topic in block.topics) {
            when(topic.topicId){
                OPTING -> handleTransactions(topic)
                UPDATE_TRANSACTION -> handleUpdateTransaction(topic)
            }
        }
    }

    private fun handleUpdateTransaction(topic: Topic) {
        TODO("Not yet implemented")
    }

    private fun handleTransactions(topic: Topic) {
        for (item in topic.items) {
            val opting = item.toOpting()
            val walletAddress = Snaps.balance(opting.walletId).walletAddress

            if(!verifySignature(opting.snapId, item.signature, walletAddress, item)){
                continue
            }
        }
    }

    private fun verifySignature(
        snapId: Int,
        signature: String,
        walletAddress: String,
        item: ApiObject
    ) = item.verifySignature(walletAddress) && // check for valid signature
            Signatures.add(snapId, signature) // make sure it is not duplicated
}