package com.coco.block

import com.coco.api.ApiObject

const val OPTING: Byte = 0
const val UPDATE_TRANSACTION: Byte = 1

class Topic(val topicId: Byte, val items: Array<ApiObject>) {

}

class Vote(
    /**
     * Block CID(content identifier)
     */
    val blockCid: String,
    /**
     * WalletId of the Validator who created the [blockCid]
     */
    val walletId: Int,
    val rejectedItems: Array<Int>
)