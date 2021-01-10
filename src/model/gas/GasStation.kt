package com.rodionov.model.gas

import com.google.gson.annotations.SerializedName
import com.rodionov.model.Coordinates
import com.rodionov.model.FuelStation
import com.rodionov.model.FuelStationServices
import com.rodionov.model.FuelStationType

class GasStation(
    type: FuelStationType = FuelStationType.GAS,
    services: List<FuelStationServices>?,
    coordinates: Coordinates?,
    brand: String?,
    id: String,
    dateOfCreation: Long?,
    creatorId: String?,
    @SerializedName("gas_types")
    val gasTypes: List<GasType>? = null
) : FuelStation(
    type = type,
    services = services,
    coordinates = coordinates,
    brand = brand,
    id = id,
    dateOfCreation = dateOfCreation,
    creatorId = creatorId
)