package com.coco.signature

import java.util.*

object Signatures {
    private val signatures: MutableList<SignaturesSnap> = mutableListOf()

    fun add(snapId: Int, signature: String) = signatures(snapId).add(signature)
    fun contains(snapId: Int, signature: String) = signatures(snapId).contains(signature)

    fun addSnap(snapId: Int) {
        signatures.add(SignaturesSnap(snapId))
    }

    private fun signatures(snapId: Int) = signatures[Collections.binarySearch(signatures, snapId)].signatures
}

data class SignaturesSnap(val snapId: Int, val signatures: TreeSet<String> = TreeSet()) : Comparable<Int> {
    override fun compareTo(other: Int) = snapId.compareTo(other)

}