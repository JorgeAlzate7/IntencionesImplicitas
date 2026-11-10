package com.example.intencionesimplicitas

import java.io.Serializable

// modelo de datos para representar una nota personal
data class Nota(
    var titulo: String,
    var contenido: String
) : Serializable
