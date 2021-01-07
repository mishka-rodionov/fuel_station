package com.rodionov.model

open class FuelStation(
    var id: String,
    var type: FuelStationType? = null,
    var services: List<FuelStationServices>? = null,
    var coordinates: Coordinates? = null,
    var brand: String? = null
)