package com.movicom.informativeapplicationcovid19.network.repositories

import androidx.lifecycle.MutableLiveData
import com.movicom.informativeapplicationcovid19.models.Country
import com.movicom.informativeapplicationcovid19.network.retrofit.Api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CountryRepository {
    private val countries: MutableLiveData<List<Country>> = MutableLiveData()
    private val message: MutableLiveData<String> = MutableLiveData()

    fun getCountries(): MutableLiveData<List<Country>> {
        return countries
    }

    fun getMessage(): MutableLiveData<String> {
        return message
    }

    /**
     * @return lista de paises.
     */
    fun callCountry() {
        var countriesList: List<Country>? = null
        Api.getCountyService()
            .getCountries().enqueue(object : Callback<List<Country>> {
                override fun onResponse(
                    call: Call<List<Country>>, response: Response<List<Country>>?
                ) {
                    // progress_bar.visibility = View.GONE
                    // svCountry!!.onActionViewExpanded()
                    if (response?.isSuccessful!!) { // si success es true
                        countriesList = response.body()
                    } else {
                        message.value = "Ha ocurrido un error ${response.code()}"
                    }
                    countries.value = countriesList
                }

                override fun onFailure(call: Call<List<Country>>, t: Throwable?) {
                    println("\n Error " + t?.message.toString())
                    message.value = "Ha ocurrido un error"
                    t?.printStackTrace()
                }
            })

    }
}