package com.coco.snap

import com.coco.api.ApiObject
import com.coco.api.UpdateTransaction

/**
 * Validates and rejects an update transaction if it fails any of the validation checks.
 *
 * Verify signature - the function checks if the signature on the transaction is valid.
 * Check for outdated or duplicated transaction - the function checks if the transaction is outdated or duplicated.
 * Balance delta check - the function checks if the sum of the balance deltas is zero.
 * External wallet check - the function checks if all the external wallets mentioned in the transaction are present in the system.
 * Member wallet ID check - the function checks if all the member wallet IDs mentioned in the transaction are valid.
 *
 * @param item the API object to reject.
 * @return true if the update transaction was rejected, false otherwise.
 */
internal fun rejectUpdateTransaction(item: ApiObject): Boolean {
    val data = item.toUpdateTransaction()

    if (!item.verifySignature(data.snapId, data.walletAddress)) {
        return true
    }
    val dtp = Snaps.dtp(data.walletAddress)

    if (dtp.lastSnapId >= data.snapId) {
        // outdated or duplicated transaction
        return true
    }

    if (data.balanceDeltas.sum() != 0L) {
        return true
    }

    if (isAnyExternalWalletMissing(data.externalDtpsData)) {
        return true
    }

    if (isMemberMissing(data.walletAddress, data.memberWalletIds)) {
        return true
    }

    return false
}

fun isMemberMissing(walletAddress: String, memberWalletIds: Array<Int>): Boolean {
    val dtp = Snaps.dtp(walletAddress)

    val totalMembers = dtp.members.size
    for (memberWalletId in memberWalletIds) {
        if (memberWalletId >= totalMembers) {
            return true
        }
    }

    return false
}

fun isAnyExternalWalletMissing(externalWallets: Array<WalletData>): Boolean {
    for (externalWallet in externalWallets) {
        val dtp = Snaps.dtp(externalWallet.walletAddress)
        for (walletAddress in externalWallet.walletAddresses) {
            if (dtp.members.binarySearchBy(walletAddress) {
                    it.walletAddress
                } == -1) {
                return true
            }

        }
    }

    return false
}

/**
Update the DTPs member balance based on the provided update transaction data.

@param nextSnap the snapshot of the DTP after the update
@param data the update transaction data
 */
fun addUpdateTransaction(nextSnap: NextSnap, data: UpdateTransaction) {
    var id = 0
    val dtp = nextSnap.dTps[data.walletAddress]!!
    for (wallet in data.newWallets) {
        dtp.members.add(BalanceData(wallet, data.balanceDeltas[id]))
        id++
    }

    for (walletId in data.memberWalletIds) {
        val balanceData = dtp.members[walletId]
        val newBalance = balanceData.balance + data.balanceDeltas[id]
        dtp.members[walletId] = BalanceData(balanceData.walletAddress, newBalance)
        id++
    }

    for (externalDtpData in data.externalDtpsData) {
        val externalDtp = nextSnap.dTps[externalDtpData.walletAddress]!!
        for (walletAddress in externalDtpData.walletAddresses) {
            val memberIndex: Int = externalDtp.members.binarySearchBy(walletAddress) {
                it.walletAddress
            }
            val balanceData = externalDtp.members[memberIndex]
            externalDtp.members[memberIndex] =
                BalanceData(balanceData.walletAddress, balanceData.balance + data.balanceDeltas[id])
            id++
        }
    }


    dtp.lastSnapId = data.snapId
    dtp.members.sort()
}