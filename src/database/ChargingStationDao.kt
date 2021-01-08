package com.rodionov.database

import com.rodionov.fromJson
import com.rodionov.model.Coordinates
import com.rodionov.model.FuelStationServices
import com.rodionov.model.FuelStationType
import com.rodionov.model.electric.ChargeType
import com.rodionov.model.electric.ChargingStation
import com.rodionov.model.electric.ConnectorType
import com.rodionov.model.gas.GasStation
import com.rodionov.model.gas.GasType
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.suspendedTransactionAsync

object ChargingStations : Table(name = "charging_stations") {
    val id: Column<String> = text("id").uniqueIndex()
    val services: Column<String> = text("services")
    val brand: Column<String> = text("brand")
    val chargingTypes: Column<String> = text("charging_types")
    val connectorTypes: Column<String> = text("connector_types")
    val coordinates: Column<String> = text("coordinates")
    override val primaryKey = PrimaryKey(id, name = "pk_charging_stations")
}

suspend fun getAllChargingStations(): List<ChargingStation> {
    return suspendedTransactionAsync {
        val query = ChargingStations.selectAll()
        println("size = ${query.count()}")
        val stations = query.map {
            ChargingStation(
                id = it[ChargingStations.id],
                type = FuelStationType.ELECTRIC,
                services = gson.fromJson<List<FuelStationServices>>(it[ChargingStations.services]),
                brand = it[ChargingStations.brand],
                chargeTypes = gson.fromJson<List<ChargeType>>(it[ChargingStations.chargingTypes]),
                coordinates = gson.fromJson<Coordinates>(it[ChargingStations.coordinates]),
                connectorTypes = gson.fromJson<List<ConnectorType>>(it[ChargingStations.connectorTypes]),
            )
        }
        stations
    }.await()
}