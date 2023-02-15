package com.coco.storage

import com.coco.snap.Snap
import java.io.*

class Storage {

    fun save(snap: Snap) {
        val file = File("snap.sr")
        ObjectOutputStream(FileOutputStream(file)).use {
            it.writeObject(snap)
        }
    }

    fun load(): Snap {
        val file = File("")
        ObjectInputStream(FileInputStream(file)).use {
            return it.readObject() as Snap
        }
    }

}