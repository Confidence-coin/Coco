package com.coco.snap

import com.coco.storage.Storage
import java.io.Serializable
import java.util.*

object Snaps {
    private var maxSnap = -1
    private val snaps = mutableListOf<Snap>()
    private val storage = Storage()

    fun boot() {
        this.snaps += storage.load()
        maxSnap = snaps.size - 1
    }

    fun addSnap(snap: Snap) {
        TODO()
    }

    fun snapWalletId(snapId: Int, walletId: Int): Int {
        if (snapId == maxSnap) {
            return walletId
        }
        val balance = snaps[snapId].balances[walletId]
        return Arrays.binarySearch(snaps[maxSnap].balances, balance)
    }

    fun balance(walletId: Int) = snaps[maxSnap].balances[walletId]

    /**
     * Return a DTP with the provided walletId, throws exception if DTP is not found
     */
    fun dtp(walletId: Int) = snaps[maxSnap].dTps[Arrays.binarySearch(snaps[maxSnap].dTps, walletId)]
}

class Snap : Serializable {
    /**
     * The snapshot id also equal to the block id
     */
    var snapId: Int = -1

    /**
     * Snap creation time, it is the middleman creation time of all the blocks with id equal to the Snap id
     */
    var creationTime: Long = -1

    /**
     * System balances ordered by wallet id; Wallet integer id the index in this array
     */
    lateinit var balances: Array<BalanceData>

    /**
     * System balances ordered by wallet id; Wallet integer id the index in this array
     */
    lateinit var validators: Array<ValidatorData>
    lateinit var dTps: Array<Dtp>
}

data class BalanceData(val walletAddress: String, val balance: Double) : Comparable<BalanceData> {
    override fun compareTo(other: BalanceData) = walletAddress.compareTo(other.walletAddress)
}

class ValidatorData : Serializable, Comparable<Int> {
    var walletId: Int = -1

    /**
     * Max 63 characters
     */
    var accessApi:String? = null
    override fun compareTo(other: Int) = walletId.compareTo(other)
}

class Dtp : Serializable, Comparable<Int> {
    var timeToNextUpdate: Long = -1
    var updateTime: Long = -1
    var walletId: Int = -1
    lateinit var members: Array<Int>
    lateinit var pendingMembers: Array<Int>
    override fun compareTo(other: Int) = walletId.compareTo(other)
}