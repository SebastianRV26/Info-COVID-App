package com.movicom.informativeapplicationcovid19.models

import java.util.*

/**
 * País.
 */
data class Country (
    private var Country:String, // nombre del país
    private var Confirmed: Int, // casos confirmados
    private var Deaths: Int,    // muertes
    private var Recovered: Int, // recuperados
    private var Active: Int,    // activos
    private var Date:Date       // fecha
)