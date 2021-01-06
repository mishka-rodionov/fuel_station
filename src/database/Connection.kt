package com.rodionov.database

import org.jetbrains.exposed.sql.Database

fun setDatabaseConnection() {
//    val temp = Database.connect("jdbc:postgresql://localhost:5432/fuel_station?characterEncoding=utf8&useUnicode=true", driver = "org.postgresql.Driver",
    val temp = Database.connect("jdbc:postgresql://localhost:5432/fs", driver = "org.postgresql.Driver",
        user = "postgres", password = "rm1989anm")
    println("database = ${temp.url}")
}