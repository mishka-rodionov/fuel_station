package com.rodionov

import com.google.gson.Gson
import com.rodionov.model.FuelStation
import com.rodionov.oktan.data.entities.model.Coordinates
import com.rodionov.oktan.data.entities.model.FuelStationServices
import com.rodionov.oktan.data.entities.model.FuelStationType
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.auth.*
import io.ktor.gson.*
import io.ktor.features.*
import io.ktor.client.*
import io.ktor.client.engine.apache.*
import io.ktor.client.engine.jetty.*
import kotlinx.coroutines.launch

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
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
    }
}

