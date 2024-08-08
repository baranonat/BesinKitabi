package com.baranonat.besinkitabi.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.baranonat.besinkitabi.model.Besin
import com.baranonat.besinkitabi.roomdb.BesinDao
import com.baranonat.besinkitabi.roomdb.BesinDatabase
import com.baranonat.besinkitabi.service.BesinAPIService
import com.baranonat.besinkitabi.util.OzelSharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BesinListesiViewModel(application:Application):AndroidViewModel(application){


    val besinler=MutableLiveData<List<Besin>>()
    val besinHataMesaji=MutableLiveData<Boolean>()
    val besinYukleniyor=MutableLiveData<Boolean>()

   private  val ozelSharedPreferences=OzelSharedPreferences(getApplication())
    private val besinApiServis=BesinAPIService()

    private val guncellemeZamani=10*60*1000*1000*1000L


     fun refreshData() {
        val kaydedilmeZamani=ozelSharedPreferences.zamaniAl()

        if(kaydedilmeZamani!=null && kaydedilmeZamani!=0L && System.nanoTime()-kaydedilmeZamani<guncellemeZamani){
            verileriRoomdanAl()
        }else{
            verileriInternettenAl()
        }
    }
        fun refreshDataFromInternet(){
            verileriInternettenAl()
        }
    private fun  verileriRoomdanAl(){
    besinYukleniyor.value=true

        viewModelScope.launch(Dispatchers.IO) {
            val besinListesi=BesinDatabase.invoke(getApplication()).besinDao().getAllBesin()
        withContext(Dispatchers.Main){
            besinleriGoster(besinListesi)
            Toast.makeText(getApplication(),"Besinleri Roomdan Aldık",Toast.LENGTH_LONG).show()
        }

        }
}

    private fun verileriInternettenAl(){
        besinYukleniyor.value=true

        viewModelScope.launch(Dispatchers.IO){
          val besinListesi=  besinApiServis.getData()
            withContext(Dispatchers.Main){
                verileriRoomaKaydet(besinListesi)
            besinYukleniyor.value=false
                Toast.makeText(getApplication(),"Besinleri İnternetten Aldık",Toast.LENGTH_LONG).show()
            }
        }

    }
    private fun besinleriGoster(besinListesi:List<Besin>){
        besinler.value=besinListesi
        besinHataMesaji.value=false
        besinYukleniyor.value=false

    }
    private fun verileriRoomaKaydet(besinListesi:List<Besin>){

        viewModelScope.launch {

            val dao=BesinDatabase(getApplication()).besinDao()

            dao.deleteAllBesin()
            val uuidListesi=dao.ınsertAllBesin(*besinListesi.toTypedArray())
            var i=0
            while(i<besinListesi.size){
                besinListesi[i].uuid=uuidListesi[i].toInt()
                i=i+1
            }
        besinleriGoster(besinListesi)

        }

        ozelSharedPreferences.zamaniKaydet(System.nanoTime())



    }



}