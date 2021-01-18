package com.rodionov.methods

import com.rodionov.database.getGasolineStations
import com.rodionov.model.gasoline.GasolineStation

data class GasolineStationListParams(
    val brand: String? = null,
    val offset: Int = 0,
    val limit: Int = 10
)

suspend fun doGasolineStationList(params: GasolineStationListParams): List<GasolineStation> {
    return getGasolineStations(offset = params.offset, limit = params.limit)
}