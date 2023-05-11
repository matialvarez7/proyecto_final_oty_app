package com.example.proyecto_final_oty_app.fragments

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.proyecto_final_oty_app.entities.Equipo
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class NewEquipoViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    val db = Firebase.firestore

    fun altaNuevoEquipo (inventario : String, nombre : String, aNet : String){
        val id = db.collection("equipos").document()
        val nuevoEquipo =  Equipo(id.id, inventario, nombre, aNet, "Disponible")
        db.collection("equipos").document(id.id)
            .set(nuevoEquipo)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot written with ID: ${documentReference}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }
}