package com.movicom.informativeapplicationcovid19.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.movicom.informativeapplicationcovid19.models.Cases
import com.movicom.informativeapplicationcovid19.network.repositories.CasesRepository

class CasesViewModel : ViewModel() {

    private val casesRepository = CasesRepository()
    var cases = MutableLiveData<List<Cases>>()
    var message = MutableLiveData<String>()

    fun refresh(slug:String) {
        callCases(slug)
        cases = casesRepository.getCases()
        processFinished()
    }

    private fun callCases(slug:String) {
        casesRepository.callCases(slug)
    }

    private fun processFinished() {
        message = casesRepository.getMessage()
    }
}