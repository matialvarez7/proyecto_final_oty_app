package com.example.proyecto_final_oty_app.entities

import java.sql.Timestamp
import java.util.Date

data class Prestamo(
    var id : String,
    var idPersonal : String,
    var fechaPrestamo : Timestamp,
    var estadoPrestamo : String
)
