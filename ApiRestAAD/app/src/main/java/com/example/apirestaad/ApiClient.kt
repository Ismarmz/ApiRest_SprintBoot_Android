package com.example.apirestaad

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

object ApiClient {
    private val client = HttpClient {
        //Seriabiliza de json a objetos
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 30000
            connectTimeoutMillis = 10000
        }
    }

    //En caso de utilizar un emulador android utilizar la siguiente IP
    //("http://10.0.2.2:8080/api/productos")

    //En caso de utilizar un remote device, ya sea por USB o por Wifi
    //Debes buscar la ip de la maquina que esta ejecutando el ApiRest
    //En la cmd el comando ipconfig y buscar la IPv4

    suspend fun getProductos(): List<Producto> {
        return client.get("http://192.168.31.121:8080/api/productos").body()
    }

    suspend fun addProducto(producto: Producto) {
        client.post("http://192.168.31.121:8080/api/productos") {
            contentType(ContentType.Application.Json)
            setBody(producto)
        }
    }

    suspend fun deleteProducto(id: Int) {
        client.delete("http://192.168.31.121:8080/api/productos/$id")
    }

    suspend fun updateProducto(producto: Producto) {
        client.put("http://192.168.31.121/api/productos/${producto.id}") {
            contentType(ContentType.Application.Json)
            setBody(producto)
        }
    }

}


