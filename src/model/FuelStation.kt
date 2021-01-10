package com.rodionov.model

import com.google.gson.annotations.SerializedName

open class FuelStation(
    @SerializedName("id")
    var id: String,
    @SerializedName("type")
    var type: FuelStationType? = null,
    @SerializedName("services")
    var services: List<FuelStationServices>? = null,
    @SerializedName("coordinates")
    var coordinates: Coordinates? = null,
    @SerializedName("brand")
    var brand: String? = null,
    @SerializedName("date_of_creation")
    var dateOfCreation: Long? = null,
    @SerializedName("creator_id")
    var creatorId: String? = null
)