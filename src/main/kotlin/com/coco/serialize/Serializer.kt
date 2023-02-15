package com.coco.serialize

import java.io.*

object Serializer {

    fun serializeToByteArray(obj: Serializable): ByteArray {
        val byteArrayOutputStream = ByteArrayOutputStream()
        val objectOutputStream = ObjectOutputStream(byteArrayOutputStream)
        objectOutputStream.writeObject(obj)
        return byteArrayOutputStream.toByteArray()
    }

    fun <T> deserializeFromByteArray(bytes: ByteArray): T {
        val byteArrayInputStream = ByteArrayInputStream(bytes)
        val objectInputStream = ObjectInputStream(byteArrayInputStream)
        return objectInputStream.readObject() as T
    }
}