package com.baranonat.besinkitabi.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.baranonat.besinkitabi.model.Besin
import com.baranonat.besinkitabi.roomdb.BesinDatabase
import kotlinx.coroutines.launch

class BesinDetayiViewModel(application: Application) :AndroidViewModel(application){

    val besinLiveData=MutableLiveData<Besin>()

    fun roomVerisiniAl(uuid:Int){

        viewModelScope.launch {
            val dao=BesinDatabase(getApplication()).besinDao()
            val besin=dao.findByIdBesin(uuid)
            besinLiveData.value=besin
        }
    }



}