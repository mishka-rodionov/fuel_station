package com.rodionov.methods

import com.rodionov.database.setNewGasolineStation
import com.rodionov.model.*
import com.rodionov.model.gasoline.GasolineStation
import com.rodionov.model.gasoline.GasolineType

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
): GasolineStation {

    val gsId = setNewGasolineStation(gasolineStationNewParams)
    return GasolineStation(
        gsId = gsId,
        services = gasolineStationNewParams.services,
        brand = gasolineStationNewParams.brand,
        gasolineTypes = gasolineStationNewParams.gasoline_types,
        coordinates = gasolineStationNewParams.coordinates
    )
}

