package com.baranonat.besinkitabi.util

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import kotlin.concurrent.Volatile

class OzelSharedPreferences {

    companion object {
        private val TIME = "time"
        private var sharedPreferences: SharedPreferences? = null

        @Volatile
        private var instance: OzelSharedPreferences? = null

        private val lock = Any()

        operator fun invoke(contex: Context) = instance ?: synchronized(lock) {
            instance ?: ozelSharedPreferencesOlustur(contex).also {
                instance = it
            }

        }

        fun ozelSharedPreferencesOlustur(contex: Context): OzelSharedPreferences {

            sharedPreferences=androidx.preference.PreferenceManager.getDefaultSharedPreferences(contex)
            return OzelSharedPreferences()
        }


    }
    fun zamaniKaydet(zaman:Long){
        sharedPreferences?.edit()?.putLong(TIME,zaman)?.apply()
    }
    fun zamaniAl()=sharedPreferences?.getLong(TIME,0)


}