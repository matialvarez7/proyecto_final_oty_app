package com.example.proyecto_final_oty_app.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Calendar
import java.util.Date


@Parcelize

data class AsignacionDocente(

    var id : String,
    var idPersonal : String,
    var idEquipo : String,
    var fechaAsignacion : Date,
    var fechaDevolucion : Date? = null

): Parcelable {
    constructor() : this("","","", Calendar.getInstance().time,null)
}
