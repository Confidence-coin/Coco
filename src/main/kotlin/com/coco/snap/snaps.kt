package com.coco.snap

import com.coco.storage.Storage
import java.io.Serializable

object Snaps {
    lateinit var snap: Snap
        private set

    private val storage = Storage()

    fun boot() {
        snap = storage.load()
    }

    fun updateSnap(snap: Snap) {
        this.snap = snap
        storage.save(snap)
    }

    fun dtp(walletAddress: String) = snap.dTps[snap.dTps.binarySearchBy(walletAddress) {
        it.walletAddress
    }]
}

class Snap : Serializable {

    /**
     * The snapshot id also equal to the block id
     */
    var snapId: Int = -1

    lateinit var snapCid: String

    /**
     * Snap creation time, it is the middleman creation time of all the blocks with id equal to the Snap id
     */
    var creationTime: Long = -1

    /**
     * System balances ordered by wallet id; Wallet integer id the index in this array
     */
    lateinit var validators: Array<ValidatorData>
    lateinit var dTps: List<Dtp>
}

data class BalanceData(val walletAddress: String, val balance: Long) : Comparable<BalanceData> {
    override fun compareTo(other: BalanceData) = walletAddress.compareTo(other.walletAddress)
}

class ValidatorData : Serializable, Comparable<Int> {
    var walletId: Int = -1

    /**
     * Max 63 characters
     */
    var accessApi: String? = null
    override fun compareTo(other: Int) = walletId.compareTo(other)
}

open class Dtp : Serializable, Comparable<String> {
    /**
     * The last snap id used in update transaction
     */
    var lastSnapId: Int = -1
    var timeToNextUpdate: Long = -1
    var updateTime: Long = -1
    lateinit var walletAddress: String
    lateinit var pendingMembers: Array<BalanceData>

    /**
     * System balances ordered by wallet id; Wallet integer id the index in this array
     */
    lateinit var members: List<BalanceData>
    override fun compareTo(other: String) = walletAddress.compareTo(other)
}

class MutableDtp : Serializable {
    var lastSnapId = -1
    lateinit var members: MutableList<BalanceData>
    lateinit var walletAddress: String
}

data class WalletData(val walletAddresses: Array<String>, val walletAddress: String)