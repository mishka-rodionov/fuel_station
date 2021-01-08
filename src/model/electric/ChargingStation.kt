package com.rodionov.model.electric

import com.google.gson.annotations.SerializedName
import com.rodionov.model.Coordinates
import com.rodionov.model.FuelStation
import com.rodionov.model.FuelStationServices
import com.rodionov.model.FuelStationType

class ChargingStation(
    type: FuelStationType = FuelStationType.ELECTRIC,
    services: List<FuelStationServices>?,
    coordinates: Coordinates?,
    brand: String?,
    id: String,
    @SerializedName("charging_types")
    val chargeTypes: List<ChargeType>? = null,
    @SerializedName("connector_types")
    val connectorTypes: List<ConnectorType>? = null
) : FuelStation(type = type, services = services, coordinates = coordinates, brand = brand, id = id)