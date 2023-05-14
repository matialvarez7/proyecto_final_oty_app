package com.example.proyecto_final_oty_app.fragments

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.proyecto_final_oty_app.entities.Equipo
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class NewEquipoViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    val db = Firebase.firestore

    fun formularioValido(nroInventario : String, nroEquipo : String, nroAnet : String) : Boolean{
        var esValido : Boolean = true
        if(nroInventario.isEmpty() || nroEquipo.isEmpty() || nroAnet.isEmpty()){
            esValido = false
        }
        return esValido
    }

    suspend fun equipoExistente(nroInventario : String ) : Boolean{
        var existe = db.collection("equipos").whereEqualTo("inventario", nroInventario).get().await()
        return !existe.isEmpty
    }

    fun altaNuevoEquipo (inventario : String, nombre : String, aNet : String){
        val id = db.collection("equipos").document()
        val nuevoEquipo = Equipo(id.id, inventario, nombre, aNet, "Disponible")
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