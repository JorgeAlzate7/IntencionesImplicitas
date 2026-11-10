package com.example.intencionesimplicitas

import java.io.Serializable

// modelo de datos para representar un contacto telefonico
data class Contacto(
    var nombre: String,
    var telefono: String
) : Serializable
