package com.baranonat.besinkitabi.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.baranonat.besinkitabi.model.Besin
import kotlin.concurrent.Volatile

@Database(entities = [Besin::class], version = 1)
abstract class BesinDatabase : RoomDatabase() {
    abstract fun besinDao(): BesinDao

    companion object{

        @Volatile
        private var instance:BesinDatabase?=null

        private val lock=Any()

      operator fun invoke(contex:Context)= instance?: synchronized(lock){
        instance?: databaseOlustur(contex).also {
            instance=it
        }

      }
        private fun databaseOlustur(contex:Context)= Room.databaseBuilder(
            contex.applicationContext,
            BesinDatabase::class.java,
            "BesinDatabase"
        ).build()



    }


}