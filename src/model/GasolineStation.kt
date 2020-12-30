package com.rodionov.model

class GasolineStation(
        type: FuelStationType = FuelStationType.GASOLINE,
        services: List<FuelStationServices>?,
        coordinates: Coordinates?,
        brand: String?,
        val gasolineTypes: List<GasolineType>? = null
): FuelStation(type = type, services = services, coordinates = coordinates, brand = brand)