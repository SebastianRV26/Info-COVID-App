package com.movicom.informativeapplicationcovid19.models

/**
 * Casos por país.
 */
data class Cases (
    var Country:String, // nombre del país
    var Confirmed: Int, // casos confirmados
    var Deaths: Int,    // muertes
    var Recovered: Int, // recuperados
    var Active: Int,    // activos
    var Date: String      // fecha
)