package com.rodionov.methods

import com.rodionov.database.GasolineStationDto
import com.rodionov.database.gson
import com.rodionov.model.*

data class GasolineStationNewParams(
    val type: FuelStationType = FuelStationType.GASOLINE,
    val services: List<FuelStationServices>? = null,
    val coordinates: Coordinates? = null,
    val brand: String? = null,
    val gasolineTypes: List<GasolineType>? = null
)

fun doGasolineStationNew(gasolineStationNewParams: GasolineStationNewParams): Map<String, Any> {

    val gasolineStation = setNewGasolineStation(gasolineStationNewParams)

    return        mutableMapOf(
            "product" to mapOf(
                "id" to gasolineStation.gs_id
            )
        )
}

fun setNewGasolineStation(gasolineStationNewParams: GasolineStationNewParams): GasolineStationDto {
    return GasolineStationDto.new {
        gs_id = java.util.UUID.randomUUID().toString()
        services = gson.toJson(gasolineStationNewParams.services)
        brand = gasolineStationNewParams.brand ?: ""
        gasolineTypes = gson.toJson(gasolineStationNewParams.gasolineTypes)
        coordinates = gson.toJson(gasolineStationNewParams.coordinates)
    }
}