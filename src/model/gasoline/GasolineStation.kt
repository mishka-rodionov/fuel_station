package com.rodionov.model.gasoline

import com.google.gson.annotations.SerializedName
import com.rodionov.model.*

class GasolineStation(
        type: FuelStationType = FuelStationType.GASOLINE,
        services: List<FuelStationServices>?,
        coordinates: Coordinates?,
        brand: String?,
        gsId: String,
        @SerializedName("gasoline_types")
        val gasolineTypes: List<GasolineType>? = null
): FuelStation(id = gsId, type = type, services = services, coordinates = coordinates, brand = brand)