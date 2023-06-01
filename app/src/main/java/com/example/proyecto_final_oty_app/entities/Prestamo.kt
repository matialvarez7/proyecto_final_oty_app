package com.example.proyecto_final_oty_app.entities

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.sql.Timestamp
import java.util.Date



@Parcelize
data class Prestamo(
    var id : String,
    var idPersonal : String,
    var fechaPrestamo : Date,
    var estadoPrestamo : String
) : Parcelable {
    constructor() : this("","",Date(2023,1,1),"")

}
