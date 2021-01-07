package com.rodionov.database

import com.google.gson.Gson
import com.rodionov.methods.GS_ID
import com.rodionov.methods.GasolineStationNewParams
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

fun getAllGasolineStations() {
    transaction {
        val station = GasolineStations.selectAll()
        println("size = ${station.count()}")
        station.forEach {
            println("gasoline_station brand = ${it[GasolineStations.brand]}")
        }
    }
}

fun setNewGasolineStation(gasolineStationNewParams: GasolineStationNewParams) : String{
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