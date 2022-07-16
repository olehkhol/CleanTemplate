package ua.`in`.khol.oleh.cleantemplate.data.database

import android.content.Context
import io.objectbox.BoxStore
import ua.`in`.khol.oleh.cleantemplate.data.database.entity.MyObjectBox

object ObjectBox {

    fun init(context: Context): BoxStore = MyObjectBox.builder()
        .androidContext(context.applicationContext)
        .build()
}