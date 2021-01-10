package com.rodionov.database

import com.google.gson.Gson
import com.rodionov.fromJson
import com.rodionov.methods.GasolineStationNewParams
import com.rodionov.model.Coordinates
import com.rodionov.model.FuelStationServices
import com.rodionov.model.FuelStationType
import com.rodionov.model.gasoline.GasolineStation
import com.rodionov.model.gasoline.GasolineType
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.suspendedTransactionAsync
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

val gson = Gson()

object GasolineStations : Table(name = "gasoline_stations") {
    val id: Column<String> = text("id").uniqueIndex()
    val services: Column<String> = text("services")
    val brand: Column<String> = text("brand")
    val gasolineTypes: Column<String> = text("gasoline_types")
    val coordinates: Column<String> = text("coordinates")
    val dateOfCreation: Column<String> = text("date_of_creation")
    val creatorId: Column<String> = text("creator_id")
    override val primaryKey = PrimaryKey(id, name = "pk_gasoline_stations")
}

suspend fun getAllGasolineStations(): List<GasolineStation> {
    return suspendedTransactionAsync {
        val query = GasolineStations.selectAll()
        println("size = ${query.count()}")
        val stations = query.map {
            GasolineStation(
                id = it[GasolineStations.id],
                type = FuelStationType.GASOLINE,
                services = gson.fromJson<List<FuelStationServices>>(it[GasolineStations.services]),
                brand = it[GasolineStations.brand],
                gasolineTypes = gson.fromJson<List<GasolineType>>(it[GasolineStations.gasolineTypes]),
                coordinates = gson.fromJson<Coordinates>(it[GasolineStations.coordinates]),
                dateOfCreation = gson.fromJson<Long>(it[GasolineStations.dateOfCreation]),
                creatorId = it[GasolineStations.creatorId]
            )
        }
        stations
    }.await()
}

fun setNewGasolineStation(gasolineStationNewParams: GasolineStationNewParams): GasolineStation {
    val gasolineId = UUID.randomUUID().toString()
    val currentDate = Calendar.getInstance().timeInMillis
    transaction {
        GasolineStations.insert {
            it[brand] = gasolineStationNewParams.brand.toString()
            it[id] = gasolineId
            it[services] = gson.toJson(gasolineStationNewParams.services)
            it[gasolineTypes] = gson.toJson(gasolineStationNewParams.gasolineTypes)
            it[coordinates] = gson.toJson(gasolineStationNewParams.coordinates)
            it[dateOfCreation] = gson.toJson(currentDate)
            it[creatorId] = gasolineStationNewParams.creatorId.toString()
        }
    }
    return gasolineStationNewParams.run {
        GasolineStation(
            type = FuelStationType.GASOLINE,
            services = services,
            coordinates = coordinates,
            brand = brand,
            id = gasolineId,
            dateOfCreation = currentDate,
            creatorId = creatorId,
            gasolineTypes = gasolineTypes
        )
    }
}