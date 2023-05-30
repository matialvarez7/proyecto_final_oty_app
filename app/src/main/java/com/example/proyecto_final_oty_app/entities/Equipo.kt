package com.example.proyecto_final_oty_app.entities

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parceler
import kotlinx.parcelize.Parcelize

@Parcelize
data class Equipo(

    var id : String,
    var inventario : String,
    var nombre : String,
    var anet : String,
    var estado : String
): Parcelable {
    constructor() : this("","","","","")

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    companion object : Parceler<Equipo> {
        override fun Equipo.write(p0: Parcel, p1: Int) {
            TODO("Not yet implemented")
        }

        override fun create(parcel: Parcel): Equipo = TODO()
    }
}

