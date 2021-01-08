package com.rodionov.model.electric

import com.google.gson.annotations.SerializedName

data class ChargeType(
    @SerializedName("charge_name")
    val chargeName: ChargeName? = null,
    @SerializedName("price_per_minute")
    val pricePerMinute: Float? = null
)