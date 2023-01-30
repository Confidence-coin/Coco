package com.coco.api

import java.nio.ByteBuffer
import java.nio.charset.StandardCharsets

const val OPT_IN: Byte = 1
const val OPT_OUT: Byte = 0

const val SIZE_INT = Int.SIZE_BYTES
const val SIZE_WALLET_ADDRESS = 32
const val SIZE_LONG = Long.SIZE_BYTES

@Suppress("ArrayInDataClass")
data class ApiObject(
    val signature: String,
    val data: ByteArray
) {
    private val buffer = ByteBuffer.wrap(data)
    fun verifySignature(walletAddress: String): Boolean {
        TODO()
    }

    fun toOpting() = OptingApi(
        buffer.int,
        buffer.int,
        buffer.int,
        buffer.get()
    )

    fun toUpdateTransaction() = UpdateTransaction(
        buffer.int,
        buffer.int,
        readStringArray(buffer, buffer.int, SIZE_WALLET_ADDRESS),
        readIntArray(buffer, buffer.int),
        readLongArray(buffer, buffer.int)
    )

    private fun readStringArray(buffer: ByteBuffer, length: Int, itemSize:Int): Array<String> {
        val itemBuffer = ByteArray(itemSize)
        return Array(length){
            StandardCharsets.UTF_8.decode(buffer.get(itemBuffer)).toString()
        }
    }

    private fun readIntArray(buffer: ByteBuffer, size: Int) = Array(size){
        buffer.int
    }

    private fun readLongArray(buffer: ByteBuffer, size: Int) = Array(size){
        buffer.long
    }
}

@Suppress("ArrayInDataClass")
data class UpdateTransaction(
    val blockId: Int,
    val dTpId: Int,
    val newWallets: Array<String>,
    val memberWalletIds: Array<Int>,
    val balances: Array<Long>
)

class ApiBuilder(capacity: Int) {

    val buffer: ByteBuffer = ByteBuffer.allocate(capacity)

    fun build() = ApiObject(sign(), buffer.array())

    private fun sign(): String {
        TODO()
    }
}

data class OptingApi(
    val snapId: Int,
    val walletId: Int,
    val dTpId: Int,
    val opting: Byte
)

fun buildOptingApi(
    blockId: Int,
    walletId: Int,
    dTpId: Int,
    opting: Byte
) = ApiBuilder(SIZE_INT * 3 + 1).run {
    buffer.putInt(blockId)
    buffer.putInt(walletId)
    buffer.putInt(dTpId)
    buffer.put(opting)
    build()
}

fun buildUpdateTransaction(
    blockId: Int,
    dTpId: Int,
    newWallets: Array<String>,
    memberWalletIds: Array<Int>,
    balances: Array<Long>,
) =
    ApiBuilder(
        SIZE_INT * 5 +
                newWallets.size * SIZE_WALLET_ADDRESS +
                memberWalletIds.size * SIZE_INT +
                balances.size * SIZE_LONG
    ).run {
        buffer.putInt(blockId)
        buffer.putInt(dTpId)
        buffer.putInt(newWallets.size)
        buffer.put(newWallets.joinToString("").toByteArray())
        buffer.putInt(memberWalletIds.size)
        memberWalletIds.forEach {
            buffer.putInt(it)
        }
        buffer.putInt(balances.size)
        balances.forEach {
            buffer.putLong(it)
        }
        build()
    }
























