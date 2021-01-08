package com.rodionov.database

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction

fun setDatabaseConnection() {
    val temp = Database.connect(
        "jdbc:postgresql://localhost:5432/fs", driver = "org.postgresql.Driver",
        user = "postgres", password = "rm1989anm"
    )

    transaction {
        addLogger(StdOutSqlLogger)
        SchemaUtils.create(GasolineStations, GasStations, ChargingStations)
    }

    println("database = ${temp.url}")
}