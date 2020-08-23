package com.movicom.informativeapplicationcovid19.network

import com.movicom.informativeapplicationcovid19.models.Country
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Servicios de paises.
 */
interface CountyService {

    @GET("countries")
    fun getCountries() : Call<Country>

    @GET("country/{code}")
    fun getCountry() : Call<Country>
}