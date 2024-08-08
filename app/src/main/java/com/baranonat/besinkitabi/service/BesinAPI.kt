package com.baranonat.besinkitabi.service

import com.baranonat.besinkitabi.model.Besin
import retrofit2.http.GET

interface BesinAPI {



    // atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json
    //Base Url: https://raw.githubusercontent.com/

    @GET("atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json")
   suspend fun getBesin():List<Besin>



}