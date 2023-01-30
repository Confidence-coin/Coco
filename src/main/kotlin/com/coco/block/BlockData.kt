package com.coco.block

@Suppress("ArrayInDataClass")
data class BlockData(
    val blockId: Int,
    /**
     * Wallet id of the validator also known as validator id
     */
    val walletId: Int,
    val blockCreationTime: Long,

    val topics:Array<Topic>,
    val votes:Array<Vote>,

    /**
     * Reference to other blocks by CID(Content identifier)
     */
    val blocksCid: Array<String>,

    /**
     * Validator signature of all the fields but the cId
     */
    val signature: String,

    /**
     * This block CID(Content identifier)
     */
    val cId: String,
)