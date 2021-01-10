package com.rodionov.model.gasoline

import com.google.gson.annotations.SerializedName
import com.rodionov.model.*

class GasolineStation(
    type: FuelStationType = FuelStationType.GASOLINE,
    services: List<FuelStationServices>?,
    coordinates: Coordinates?,
    brand: String?,
    id: String,
    dateOfCreation: Long?,
    creatorId: String?,
    @SerializedName("gasoline_types")
    val gasolineTypes: List<GasolineType>? = null
) : FuelStation(
    id = id,
    type = type,
    services = services,
    coordinates = coordinates,
    brand = brand,
    dateOfCreation = dateOfCreation,
    creatorId = creatorId
)