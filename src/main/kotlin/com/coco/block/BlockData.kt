package com.coco.block

@Suppress("ArrayInDataClass")
data class BlockData(
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
    val redScores: Array<Int>,

    /**
     * Validator signature of all the fields but the cId
     */
    val signature: String,

    /**
     * This block CID(Content identifier)
     */
    val cId: String,
) : Comparable<BlockData> {
    override fun compareTo(other: BlockData) = cId.compareTo(other.cId)

}