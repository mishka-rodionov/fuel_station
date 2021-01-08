package com.rodionov.model.gas

import com.google.gson.annotations.SerializedName

class GasType(
    @SerializedName("gas_name")
    val gasName: GasName? = null,
    @SerializedName("price_per_liter")
    val pricePerLiter: Float? = null
)