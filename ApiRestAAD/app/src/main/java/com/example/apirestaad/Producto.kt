package com.example.apirestaad

import kotlinx.serialization.Serializable

@Serializable
data class Producto(
    val id: Int? = null,
    val nombre: String,
    val precio: Double
)

