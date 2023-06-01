package com.example.proyecto_final_oty_app.entities


import android.os.Parcelable

import kotlinx.parcelize.Parcelize

@Parcelize
data class Equipo(

    var id : String,
    var inventario : String,
    var nombre : String,
    var anet : String,
    var estado : String
): Parcelable {
    constructor() : this("", "", "", "", "")

}

