package com.example.proyecto_final_oty_app.entities

import java.util.Date

data class Prestamo(
    private var id : String,
    private var idPersonal : String,
    private var fechaPrestamo : Date,
    private var estadoPrestamo : String
)
