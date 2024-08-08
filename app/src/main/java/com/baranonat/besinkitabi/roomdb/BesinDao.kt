package com.baranonat.besinkitabi.roomdb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.baranonat.besinkitabi.model.Besin

@Dao
interface BesinDao {

    @Insert
   suspend fun ınsertAllBesin(vararg besin:Besin):List<Long>

    @Query("Select * from besin")
   suspend fun getAllBesin(): List<Besin>

    @Query("Select * from besin where uuid=:besinId")
   suspend fun findByIdBesin(besinId:Int):Besin

    @Query("Delete From besin")
   suspend fun deleteAllBesin()

}