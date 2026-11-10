package com.example.intencionesimplicitas

import java.io.Serializable

// modelo de datos para representar un lugar turistico o de interes
data class Lugar(
    val nombre: String,
    val descripcion: String,
    val imagen: Int
) : Serializable
