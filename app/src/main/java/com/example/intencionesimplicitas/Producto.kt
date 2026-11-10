package com.example.intencionesimplicitas

import java.io.Serializable

// modelo de datos para producto con soporte de nombre imagen y precio
data class Producto(
    val nombre: String,
    val imagen: Int = 0,
    val precio: Double = 0.0
) : Serializable
