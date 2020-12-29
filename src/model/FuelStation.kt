package com.rodionov.model

import com.rodionov.oktan.data.entities.model.Coordinates
import com.rodionov.oktan.data.entities.model.FuelStationServices
import com.rodionov.oktan.data.entities.model.FuelStationType

open class FuelStation(
    var type: FuelStationType? = null,
    var services: List<FuelStationServices>? = null,
    var coordinates: Coordinates? = null,
    var brand: String? = null
)