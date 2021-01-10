package com.rodionov.methods

import com.google.gson.annotations.SerializedName
import com.rodionov.database.setNewGasolineStation
import com.rodionov.model.*
import com.rodionov.model.gasoline.GasolineType

data class GasolineStationNewParams(
    @SerializedName("type")
    val type: FuelStationType = FuelStationType.GASOLINE,
    @SerializedName("services")
    val services: List<FuelStationServices>? = null,
    @SerializedName("coordinates")
    val coordinates: Coordinates? = null,
    @SerializedName("brand")
    val brand: String? = null,
    @SerializedName("gasoline_types")
    val gasolineTypes: List<GasolineType>? = null,
    @SerializedName("creator_id")
    val creatorId: String? = null
)

fun doGasolineStationNew(gasolineStationNewParams: GasolineStationNewParams) = setNewGasolineStation(gasolineStationNewParams)

