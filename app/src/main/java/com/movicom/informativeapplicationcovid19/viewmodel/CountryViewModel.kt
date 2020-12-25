package com.movicom.informativeapplicationcovid19.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.movicom.informativeapplicationcovid19.models.Country
import com.movicom.informativeapplicationcovid19.network.repositories.CountryRepository

class CountryViewModel : ViewModel() {

    private val countryRepository = CountryRepository()
    var countries = MutableLiveData<List<Country>>()
    var message = MutableLiveData<String>()

    fun refresh() {
        callComics()
        countries = countryRepository.getCountries()
        processFinished()
    }

    private fun callComics() {
        countryRepository.callCountry()
    }

    private fun processFinished() {
        message = countryRepository.getMessage()
    }

    fun searchCountry(countryName:String): Country?{
        countries.value?.forEach {
            if (it.Country == countryName){
                return it
            }
        }
        return null
    }
}