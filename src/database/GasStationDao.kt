package com.rodionov.database

import com.rodionov.fromJson
import com.rodionov.methods.GasStationNewParams
import com.rodionov.model.Coordinates
import com.rodionov.model.FuelStationServices
import com.rodionov.model.FuelStationType
import com.rodionov.model.gas.GasStation
import com.rodionov.model.gas.GasType
import com.rodionov.model.gasoline.GasolineStation
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.suspendedTransactionAsync
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

object GasStations : Table(name = "gas_stations") {
    val id: Column<String> = text("id").uniqueIndex()
    val services: Column<String> = text("services")
    val brand: Column<String> = text("brand")
    val gasTypes: Column<String> = text("gas_types")
    val coordinates: Column<String> = text("coordinates")
    val dateOfCreation: Column<String> = text("date_of_creation")
    val creatorId: Column<String> = text("creator_id")
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
                coordinates = gson.fromJson<Coordinates>(it[GasStations.coordinates]),
                dateOfCreation = gson.fromJson<Long>(it[GasStations.dateOfCreation]),
                creatorId = it[GasStations.creatorId]
            )
        }
        stations
    }.await()
}

fun setNewGasStation(gasStationNewParams: GasStationNewParams): GasStation {
    val gasId = UUID.randomUUID().toString()
    val currentDate = Calendar.getInstance().timeInMillis
    transaction {
        GasStations.insert {
            it[brand] = gasStationNewParams.brand.toString()
            it[id] = gasId
            it[services] = gson.toJson(gasStationNewParams.services)
            it[gasTypes] = gson.toJson(gasStationNewParams.gasTypes)
            it[coordinates] = gson.toJson(gasStationNewParams.coordinates)
            it[dateOfCreation] = gson.toJson(currentDate)
            it[creatorId] = gasStationNewParams.creatorId.toString()
        }
    }
    return gasStationNewParams.run {
        GasStation(
            type = FuelStationType.GAS,
            services = services,
            coordinates = coordinates,
            brand = brand,
            id = gasId,
            dateOfCreation = currentDate,
            creatorId = creatorId,
            gasTypes = gasTypes
        )
    }
}