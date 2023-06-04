package com.example.proyecto_final_oty_app.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.sql.Date

@Parcelize
data class PrestamoFinal(
    //Datos del prestamo
    var idPrestamo : String,
    var fechaPrestamo : Date,
    var estadoPrestamo : String,
    //Datos del personal
    var nombre : String,
    var apellido : String,
    //Listado de equipos asignados al prestamo
    var itemsPrestamo : MutableList<ItemPrestamo>
) : Parcelable {
    constructor() : this ("",Date(2024, 1, 1),"","","", mutableListOf())
}
