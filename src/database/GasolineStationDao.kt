package com.rodionov.database

import com.google.gson.Gson
import com.rodionov.fromJson
import com.rodionov.methods.GS_ID
import com.rodionov.methods.GasolineStationNewParams
import com.rodionov.model.Coordinates
import com.rodionov.model.FuelStationServices
import com.rodionov.model.FuelStationType
import com.rodionov.model.gasoline.GasolineStation
import com.rodionov.model.gasoline.GasolineType
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.suspendedTransactionAsync
import org.jetbrains.exposed.sql.transactions.transaction

val gson = Gson()

object GasolineStations : Table(name = "gasoline_stations") {
    val gsId: Column<String> = text("gs_id").uniqueIndex()
    val services: Column<String> = text("services")
    val brand: Column<String> = text("brand")
    val gasolineTypes: Column<String> = text("gasoline_types")
    val coordinates: Column<String> = text("coordinates")
    override val primaryKey = PrimaryKey(gsId, name = "pk_gasoline_stations")
}

suspend fun getAllGasolineStations(): List<GasolineStation> {
    return suspendedTransactionAsync {
        val query = GasolineStations.selectAll()
        println("size = ${query.count()}")
        val stations = query.map {
            GasolineStation(
                gsId = it[GasolineStations.gsId],
                type = FuelStationType.GASOLINE,
                services = gson.fromJson<List<FuelStationServices>>(it[GasolineStations.services]),
                brand = it[GasolineStations.brand],
                gasolineTypes = gson.fromJson<List<GasolineType>>(it[GasolineStations.gasolineTypes]),
                coordinates = gson.fromJson<Coordinates>(it[GasolineStations.coordinates])
            )
        }
        stations
    }.await()
}

fun setNewGasolineStation(gasolineStationNewParams: GasolineStationNewParams): String {
    GS_ID = java.util.UUID.randomUUID().toString()
    transaction {
        GasolineStations.insert {
            it[brand] = gasolineStationNewParams.brand.toString()
            it[gsId] = GS_ID
            it[services] = gson.toJson(gasolineStationNewParams.services)
            it[gasolineTypes] = gson.toJson(gasolineStationNewParams.gasoline_types)
            it[coordinates] = gson.toJson(gasolineStationNewParams.coordinates)
        }
    }
    return GS_ID
}