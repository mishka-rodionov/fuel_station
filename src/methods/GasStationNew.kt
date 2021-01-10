package com.rodionov.methods

import com.google.gson.annotations.SerializedName
import com.rodionov.database.setNewGasStation
import com.rodionov.model.Coordinates
import com.rodionov.model.FuelStationServices
import com.rodionov.model.FuelStationType
import com.rodionov.model.gas.GasStation
import com.rodionov.model.gas.GasType

data class GasStationNewParams(
    @SerializedName("type")
    val type: FuelStationType = FuelStationType.GAS,
    @SerializedName("services")
    val services: List<FuelStationServices>? = null,
    @SerializedName("coordinates")
    val coordinates: Coordinates? = null,
    @SerializedName("brand")
    val brand: String? = null,
    @SerializedName("gas_types")
    val gasTypes: List<GasType>? = null,
    @SerializedName("creator_id")
    val creatorId: String? = null
)

fun doGasStationNew(gasStationNewParams: GasStationNewParams) = setNewGasStation(gasStationNewParams)