package com.example.proyecto_final_oty_app.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ItemPrestamoInterno(
    var idItem : String,
    var estadoItem : String,
    var idEquipo : String,
    var inventarioEquipo : String,
    var nombreEquipo : String,
    var anetEquipo : String
) : Parcelable {
    constructor() : this("","","","","","")
}
