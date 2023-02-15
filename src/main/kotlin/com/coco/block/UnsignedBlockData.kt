package com.coco.block

@Suppress("ArrayInDataClass")
data class UnsignedBlockData(
    /**
     * The CID(Content identifier) of snap [blockId]-2
     */
    val baseSnap: String,

    /**
     * A running block id
     */
    val blockId: Int,

    /**
     * Wallet id of the validator also known as validator id
     */
    val walletId: Int,
    val blockCreationTime: Long,

    val topics: Array<Topic>,
    val votes: Array<Vote>,

    /**
     * Array of validators who scored Red
     */
    val redScores: Array<Int>
)