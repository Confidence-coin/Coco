package com.coco.storage

import com.coco.snap.Snap
import java.io.*

class Storage {

    fun save(snap: Array<Snap>) {
        val file = File("")
        ObjectOutputStream(FileOutputStream(file)).use {
            it.writeObject(snap)
        }
    }

    fun load(): Array<Snap> {
        val file = File("")
        ObjectInputStream(FileInputStream(file)).use {
            @Suppress("UNCHECKED_CAST")
            return it.readObject() as Array<Snap>
        }
    }

}