package com.movicom.informativeapplicationcovid19.network

import com.movicom.informativeapplicationcovid19.models.Cases
import com.movicom.informativeapplicationcovid19.models.Country
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Servicios de paises.
 */
interface CountyService {

    @GET("countries")
    fun getCountries() : Call<List<Country>>

    @GET("dayone/country/{country}")
    fun getCountry(@Path("country")country:String) : Call<Cases>
}