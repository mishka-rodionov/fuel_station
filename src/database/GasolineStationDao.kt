package com.rodionov.database

import com.google.gson.Gson
import com.rodionov.model.GasolineStation
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.transactions.transaction

val gson = Gson()

object GasolineStations : IntIdTable() {
    val gsId: Column<String> = text("gs_id").uniqueIndex()
    val services: Column<String> = text("services")
    val brand: Column<String> = text("brand")
    val gasolineTypes: Column<String> = text("gasoline_types")
    val coordinates: Column<String> = text("coordinates")
    override val primaryKey = PrimaryKey(id)
}

class GasolineStationDto(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<GasolineStationDto>(GasolineStations)
    var gs_id by GasolineStations.gsId
    var services     by GasolineStations.services
    var brand by GasolineStations.brand
    var gasolineTypes by GasolineStations.gasolineTypes
    var coordinates by GasolineStations.coordinates
}

fun getAllGasolineStations() {
    transaction {
        val station = GasolineStationDto.all()
        println("size = ${station.count()}")
        station.forEach {
            println("gasoline_station brand = ${it.brand}")
        }
    }
}
