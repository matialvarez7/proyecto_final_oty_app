package com.example.proyecto_final_oty_app.fragments

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.proyecto_final_oty_app.entities.Equipo
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class EditDetalleEquipoViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    var  equiposCollection = Firebase.firestore.collection("equipos")



    suspend fun existeEquipo(equipo: Equipo): Boolean {
        val query = equiposCollection
            .whereEqualTo("inventario", equipo.inventario)
            .whereEqualTo("nombre", equipo.nombre)
            .whereEqualTo("anet", equipo.anet)
            .whereEqualTo("estado", equipo.estado)
            .get()
            .await()
        return !query.isEmpty
    }


    fun actualizarEquipo(equipo: Equipo) {
        val db = Firebase.firestore
        db.collection("equipos").document(equipo.id!!)
            .set(equipo)
            .addOnSuccessListener {
                Log.d(TAG, "Equipo actualizado correctamente")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error al actualizar equipo", e)
            }
    }
}