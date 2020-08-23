package com.movicom.informativeapplicationcovid19.network

import com.movicom.informativeapplicationcovid19.environments.apiCovidBaseUrl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

/**
 * API de Covid-19.
 */
class Api {

    private var retrofit: Retrofit

    // services
    private var countyService: CountyService

    init {
        retrofit = Retrofit.Builder()
            .baseUrl("https://api.covid19api.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        countyService = retrofit.create(CountyService::class.java)
    }

    companion object {
        private var instance: Api ?= null
        fun getInstance(): Api {
            if (instance == null) {
                instance = Api()
            }
            return instance as Api
        }
    }

    fun getCountyService():CountyService{
        return countyService
    }
}