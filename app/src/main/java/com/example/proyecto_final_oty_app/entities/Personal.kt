package com.example.proyecto_final_oty_app.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize

data class Personal(
    var id: String,
    var dni : String,
    var nombre : String,
    var apellido : String,
    var area : String,
    var estado:String="Activo"
): Parcelable {
    constructor() : this("","","","","")
}

