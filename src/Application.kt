package com.rodionov

import com.google.gson.Gson
import com.rodionov.database.getAllGasolineStations
import com.rodionov.database.setDatabaseConnection
import com.rodionov.methods.doGasolineStationNew
import com.rodionov.model.FuelStation
import com.rodionov.model.Coordinates
import com.rodionov.model.FuelStationServices
import com.rodionov.model.FuelStationType
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.auth.*
import io.ktor.gson.*
import io.ktor.features.*
import io.ktor.client.*
import io.ktor.client.engine.jetty.*
import io.ktor.request.receive
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

//fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)
fun main(args: Array<String>): Unit {
    embeddedServer(Netty, 8080) {
        println("database = ")
            setDatabaseConnection()
            getAllGasolineStations()

        install(Authentication) {
        }

        install(ContentNegotiation) {
            gson {
            }
        }

        val client = HttpClient(Jetty) {
        }

        routing {
            get("/") {
                call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)
            }

            get("/json/gson") {
                call.respond(mapOf("hello" to "world"))
            }

            get("/fuel_stations") {
                val station = FuelStation(
                    type = FuelStationType.GASOLINE,
                    services = listOf(
                        FuelStationServices.BANK_TERMINAL,
                        FuelStationServices.CAFE,
                        FuelStationServices.CAR_WASH
                    ),
                    coordinates = Coordinates(45.42736, 54.2864),
                    brand = "THK"
                )
                call.respond(
                    Gson().toJson(station)
                )
            }
            post("/gasoline_station/new") {
                call.respond(doGasolineStationNew(call.receive()))
            }
        }
    }.start(wait = true)
}

//@Suppress("unused") // Referenced in application.conf
//@kotlin.jvm.JvmOverloads
//fun Application.module(testing: Boolean = false) {
//    install(Authentication) {
//    }
//
//    install(ContentNegotiation) {
//        gson {
//        }
//    }
//
//    val client = HttpClient(Jetty) {
//    }
//
//    routing {
//        get("/") {
//            call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)
//        }
//
//        get("/json/gson") {
//            call.respond(mapOf("hello" to "world"))
//        }
//
//        get("/fuel_stations") {
//            val station = FuelStation(
//                type = FuelStationType.GASOLINE,
//                services = listOf(
//                    FuelStationServices.BANK_TERMINAL,
//                    FuelStationServices.CAFE,
//                    FuelStationServices.CAR_WASH
//                ),
//                coordinates = Coordinates(45.42736, 54.2864),
//                brand = "THK"
//            )
//            call.respond(
//                Gson().toJson(station)
//            )
//        }
//    }
//}

