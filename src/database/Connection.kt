package com.rodionov.database

import org.jetbrains.exposed.sql.Database

fun setDatabaseConnection() {
    val temp = Database.connect("jdbc:postgresql://localhost:5432/fuel_station", driver = "org.postgresql.Driver",
        user = "postgres", password = "rm1989anm")
    println("database = ${temp.url}")
}