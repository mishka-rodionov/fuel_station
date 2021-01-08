package com.rodionov.model.gasoline

import com.google.gson.annotations.SerializedName
import com.rodionov.model.gasoline.GasolineName

class GasolineType(
        @SerializedName("name")
        val name: GasolineName? = null,
        @SerializedName("price_per_liter")
        val pricePerLiter: Float? = null,
        @SerializedName("real_oktan_number")
        val realOktanNumber: Float? = null
)