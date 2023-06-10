package com.example.proyecto_final_oty_app.entities

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ItemPrestamo(
    var id : String,
    var idEquipo : String,
    var idPrestamo : String,
    var estadoItem : String
) : Parcelable {
    constructor() : this("","","","")

}