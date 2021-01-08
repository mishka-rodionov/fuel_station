package com.rodionov.methods

import com.google.gson.annotations.SerializedName
import com.rodionov.database.setNewChargingStation
import com.rodionov.model.Coordinates
import com.rodionov.model.FuelStationServices
import com.rodionov.model.FuelStationType
import com.rodionov.model.electric.ChargeType
import com.rodionov.model.electric.ChargingStation
import com.rodionov.model.electric.ConnectorType

data class ChargingStationNewParams(
    @SerializedName("type")
    val type: FuelStationType = FuelStationType.ELECTRIC,
    @SerializedName("services")
    val services: List<FuelStationServices>? = null,
    @SerializedName("coordinates")
    val coordinates: Coordinates? = null,
    @SerializedName("brand")
    val brand: String? = null,
    @SerializedName("charge_types")
    val chargeTypes: List<ChargeType>? = null,
    @SerializedName("connector_types")
    val connectorTypes: List<ConnectorType>? = null
)

fun doChargingStationNew(
    chargingStationNewParams: ChargingStationNewParams
): ChargingStation {
    val id = setNewChargingStation(chargingStationNewParams)
    return ChargingStation(
        id = id,
        services = chargingStationNewParams.services,
        brand = chargingStationNewParams.brand,
        chargeTypes = chargingStationNewParams.chargeTypes,
        coordinates = chargingStationNewParams.coordinates,
        connectorTypes = chargingStationNewParams.connectorTypes
    )
}