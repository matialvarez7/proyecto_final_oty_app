package com.example.proyecto_final_oty_app.fragments

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DetalleEquipoViewModel : ViewModel() {
    private val firestore = FirebaseFirestore.getInstance()
    private val equiposCollection = firestore.collection("equipos")


    suspend fun eliminarEquipo(idEquipo: String) = withContext(Dispatchers.IO) {
        actualizarEstadoDelEquipo(idEquipo,"inactivo")

    }
    private fun actualizarEstadoDelEquipo(idEquipo: String, nuevoEstado: String) {

        equiposCollection.document(idEquipo).update("estado",nuevoEstado)
            .addOnSuccessListener {
                Log.d(ContentValues.TAG, "Equipo actualizado correctamente")
            }
            .addOnFailureListener { e ->
                Log.w(ContentValues.TAG, "Error al actualizar equipo", e)
            }
    }
}