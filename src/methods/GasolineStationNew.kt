package com.rodionov.methods

import com.rodionov.database.GasolineStationDto
import com.rodionov.database.GasolineStations
import com.rodionov.database.gson
import com.rodionov.database.setNewGasolineStation
import com.rodionov.model.*
import javafx.application.Application.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.transactions.transaction

data class GasolineStationNewParams(
    val type: FuelStationType = FuelStationType.GASOLINE,
    val services: List<FuelStationServices>? = null,
    val coordinates: Coordinates? = null,
    val brand: String? = null,
    val gasoline_types: List<GasolineType>? = null
)

var GS_ID: String = "-1"

fun doGasolineStationNew(
    gasolineStationNewParams: GasolineStationNewParams
) : Map<String, Any> {

//    setNewGasolineStation(gasolineStationNewParams)
    lateinit var gasolineStation: GasolineStationDto
    transaction {
        gasolineStation = GasolineStationDto.find { GasolineStations.gsId eq GS_ID }.first()
    }
    return mapOf(
        "gasoline_station" to gasolineStation
    )

}
