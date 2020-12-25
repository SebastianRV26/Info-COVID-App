package com.movicom.informativeapplicationcovid19.network.repositories

import androidx.lifecycle.MutableLiveData
import com.movicom.informativeapplicationcovid19.models.Cases
import com.movicom.informativeapplicationcovid19.network.retrofit.Api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CasesRepository {
    private val cases: MutableLiveData<List<Cases>> = MutableLiveData()
    private val message: MutableLiveData<String> = MutableLiveData()

    fun getCases(): MutableLiveData<List<Cases>> {
        return cases
    }

    fun getMessage(): MutableLiveData<String> {
        return message
    }

    /**
     * @return lista de casos.
     */
    fun callCases(slug: String) {
        var casesList: List<Cases>? = null
        Api.getCountyService()
            .getCountry(slug).enqueue(object : Callback<List<Cases>> {
                override fun onResponse(
                    call: Call<List<Cases>>, response: Response<List<Cases>>?
                ) {
                    if (response?.isSuccessful!!) { // si success es true
                        casesList = response.body()
                    } else {
                        message.value = "Ha ocurrido un error ${response.code()}"
                    }
                    cases.value = casesList
                }

                override fun onFailure(call: Call<List<Cases>>, t: Throwable?) {
                    println("\n Error " + t?.message.toString())
                    message.value = "Ha ocurrido un error"
                    t?.printStackTrace()
                }
            })
    }
}