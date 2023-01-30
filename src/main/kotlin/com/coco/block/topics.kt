package com.coco.block

import com.coco.api.ApiObject

const val OPTING:Byte = 0
const val UPDATE_TRANSACTION:Byte = 1

class Topic(val topicId: Byte, val items:Array<ApiObject>){

}

class Vote(
    /**
     * Block CID(content identifier)
     */
    val blockCid:String,
    val votes:Array<TopicVote>)

class TopicVote(
    val topicId:Int,
    val rejectedItems: Array<Int>
)