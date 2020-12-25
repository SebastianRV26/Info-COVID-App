package com.movicom.informativeapplicationcovid19.network.retrofit

import com.movicom.informativeapplicationcovid19.network.Environments
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

/**
 * API de Covid-19.
 */
object Api {

    private var retrofit: Retrofit

    // services
    private var countyService: CountyService

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(Environments.COVID_BASE_URL)
            .client(OkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        countyService = retrofit.create(CountyService::class.java)
    }

    fun getCountyService(): CountyService {
        return countyService
    }
}