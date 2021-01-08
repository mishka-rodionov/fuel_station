package com.rodionov.database

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object ChargingStations : Table(name = "charging_stations") {
    val id: Column<String> = text("id").uniqueIndex()
    val services: Column<String> = text("services")
    val brand: Column<String> = text("brand")
    val chargingTypes: Column<String> = text("charging_types")
    val connectorTypes: Column<String> = text("connector_types")
    val coordinates: Column<String> = text("coordinates")
    override val primaryKey = PrimaryKey(id, name = "pk_charging_stations")
}