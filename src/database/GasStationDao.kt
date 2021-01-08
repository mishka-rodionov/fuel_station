package com.rodionov.database

import com.rodionov.fromJson
import com.rodionov.model.Coordinates
import com.rodionov.model.FuelStationServices
import com.rodionov.model.FuelStationType
import com.rodionov.model.gas.GasStation
import com.rodionov.model.gas.GasType
import com.rodionov.model.gasoline.GasolineStation
import com.rodionov.model.gasoline.GasolineType
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.suspendedTransactionAsync

object GasStations : Table(name = "gas_stations") {
    val id: Column<String> = text("id").uniqueIndex()
    val services: Column<String> = text("services")
    val brand: Column<String> = text("brand")
    val gasTypes: Column<String> = text("gas_types")
    val coordinates: Column<String> = text("coordinates")
    override val primaryKey = PrimaryKey(id, name = "pk_gas_stations")
}

suspend fun getAllGasStations(): List<GasStation> {
    return suspendedTransactionAsync {
        val query = GasStations.selectAll()
        println("size = ${query.count()}")
        val stations = query.map {
            GasStation(
                id = it[GasStations.id],
                type = FuelStationType.GAS,
                services = gson.fromJson<List<FuelStationServices>>(it[GasStations.services]),
                brand = it[GasStations.brand],
                gasTypes = gson.fromJson<List<GasType>>(it[GasStations.gasTypes]),
                coordinates = gson.fromJson<Coordinates>(it[GasStations.coordinates])
            )
        }
        stations
    }.await()
}