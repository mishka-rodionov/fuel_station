package com.rodionov.methods

//import com.rodionov.database.GasolineStationDto
import com.rodionov.database.GasolineStations
import com.rodionov.database.gson
import com.rodionov.database.setNewGasolineStation
import com.rodionov.model.*
import javafx.application.Application.launch
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
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
): Map<String, Any> {

    val gsId = setNewGasolineStation(gasolineStationNewParams)
    return mapOf(
        "gs_id" to gsId,
        "services" to gasolineStationNewParams.services.toString(),
        "coordinates" to gasolineStationNewParams.coordinates.toString(),
        "brand" to gasolineStationNewParams.brand.toString(),
        "gasoline_types" to gasolineStationNewParams.gasoline_types.toString()
    )

}
