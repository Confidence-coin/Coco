package com.coco.ipfs

import io.ipfs.api.IPFS
import io.ipfs.api.NamedStreamable


class FileManager {

    fun publish() {
        val ipfs = IPFS("/ip4/127.0.0.1/tcp/5001")
        val file = NamedStreamable.ByteArrayWrapper("file.txt", "Hello IPFS!".toByteArray())
        val node = ipfs.add(file)[0]
        val cid = node.hash.toBase58()
        println("File published with CID: $cid")
    }

    fun subscribePubSub(topic: String) {
        val ipfs = IPFS("/ip4/127.0.0.1/tcp/5001")

        ipfs.pubsub.sub(topic, { result ->

        }, { exception ->

        })
    }
}