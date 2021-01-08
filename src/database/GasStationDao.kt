package com.rodionov.database

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object GasStations : Table(name = "gas_stations") {
    val id: Column<String> = text("id").uniqueIndex()
    val services: Column<String> = text("services")
    val brand: Column<String> = text("brand")
    val gasTypes: Column<String> = text("gas_types")
    val coordinates: Column<String> = text("coordinates")
    override val primaryKey = PrimaryKey(id, name = "pk_gas_stations")
}