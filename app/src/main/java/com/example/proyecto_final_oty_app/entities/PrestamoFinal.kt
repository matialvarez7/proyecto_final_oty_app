package com.example.proyecto_final_oty_app.entities

import java.sql.Date
import java.sql.Timestamp

data class PrestamoFinal(
    //Datos del prestamo
    var idPrestamo : String,
    var fechaPrestamo : Date,
    var estadoPrestamo : String,
    //Datos del personal
    var dni : String,
    var nombre : String,
    var apellido : String,
    var area : String,
    //Listado de equipos asignados al prestamo
    var equipos : MutableList<Equipo>
)
