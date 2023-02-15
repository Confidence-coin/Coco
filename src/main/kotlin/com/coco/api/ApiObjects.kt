package com.coco.api

import com.coco.serialize.Serializer
import com.coco.signature.Signatures
import com.coco.snap.WalletData
import java.io.Serializable

const val OPT_IN: Byte = 1
const val OPT_OUT: Byte = 0

const val SIZE_INT = Int.SIZE_BYTES
const val SIZE_WALLET_ADDRESS = 32
const val SIZE_LONG = Long.SIZE_BYTES

@Suppress("ArrayInDataClass")
data class ApiObject(
    val signature: String,
    val data: ByteArray,
    val type: Byte,
) {
    fun verifySignature(
        snapId: Int,
        walletAddress: String
    ): Boolean {
        return verifySignature(signature) && // check for valid signature
                Signatures.add(snapId, walletAddress) // and make sure it is not duplicated
    }

    private fun verifySignature(walletAddress: String): Boolean {
        TODO("Not yet implemented")
    }

    fun toOpting(): OptingApi = Serializer.deserializeFromByteArray(data)

    fun toUpdateTransaction(): UpdateTransaction = Serializer.deserializeFromByteArray(data)
}

@Suppress("ArrayInDataClass")
data class UpdateTransaction(
    val snapId: Int,
    val walletAddress: String,
    val newWallets: Array<String>,
    val memberWalletIds: Array<Int>,
    val externalDtpsData: Array<WalletData>,
    val balanceDeltas: Array<Long>,
)

data class OptingApi(
    val snapId: Int,
    val walletId: Int,
    val dTpId: Int,
    val opting: Byte
) : Serializable


























