package com.example.proyecto_final_oty_app.entities

import java.util.Calendar
import java.util.Date

data class Prestamo(
    var id : String,
    var idPersonal : String,
    var fechaPrestamo : String,
    var estadoPrestamo : String
) {
    constructor() : this("","", "", "")

}
