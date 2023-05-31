package com.example.proyecto_final_oty_app.entities

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parceler
import kotlinx.parcelize.Parcelize


@Parcelize
data class Personal(
    var id: String? = null,
    var dni : String,
    var nombre : String,
    var apellido : String,
    var area : String
): Parcelable {
    constructor() : this("","","","","")

}

