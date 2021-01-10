package com.rodionov.database

import com.rodionov.fromJson
import com.rodionov.methods.ChargingStationNewParams
import com.rodionov.model.Coordinates
import com.rodionov.model.FuelStationServices
import com.rodionov.model.FuelStationType
import com.rodionov.model.electric.ChargeType
import com.rodionov.model.electric.ChargingStation
import com.rodionov.model.electric.ConnectorType
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.suspendedTransactionAsync
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

object ChargingStations : Table(name = "charging_stations") {
    val id: Column<String> = text("id").uniqueIndex()
    val services: Column<String> = text("services")
    val brand: Column<String> = text("brand")
    val chargingTypes: Column<String> = text("charging_types")
    val connectorTypes: Column<String> = text("connector_types")
    val coordinates: Column<String> = text("coordinates")
    val dateOfCreation: Column<String> = text("date_of_creation")
    val creatorId: Column<String> = text("creator_id")
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
                dateOfCreation = gson.fromJson<Long>(it[ChargingStations.dateOfCreation]),
                creatorId = it[ChargingStations.creatorId]
            )
        }
        stations
    }.await()
}

fun setNewChargingStation(chargingStationNewParams: ChargingStationNewParams): ChargingStation {
    val chargingId = UUID.randomUUID().toString()
    val currentDate = Calendar.getInstance().timeInMillis
    transaction {
        ChargingStations.insert {
            it[brand] = chargingStationNewParams.brand.toString()
            it[id] = chargingId
            it[services] = gson.toJson(chargingStationNewParams.services)
            it[chargingTypes] = gson.toJson(chargingStationNewParams.chargeTypes)
            it[coordinates] = gson.toJson(chargingStationNewParams.coordinates)
            it[connectorTypes] = gson.toJson(chargingStationNewParams.connectorTypes)
            it[dateOfCreation] = gson.toJson(currentDate)
            it[creatorId] = chargingStationNewParams.creatorId.toString()
        }
    }
    return chargingStationNewParams.run {
        ChargingStation(
            type = FuelStationType.ELECTRIC,
            services = services,
            coordinates = coordinates,
            brand = brand,
            id = chargingId,
            dateOfCreation = currentDate,
            creatorId = creatorId,
            chargeTypes = chargeTypes,
            connectorTypes = connectorTypes
        )
    }
}