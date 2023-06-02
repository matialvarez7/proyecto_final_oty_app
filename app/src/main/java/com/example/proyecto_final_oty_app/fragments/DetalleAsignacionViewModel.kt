package com.example.proyecto_final_oty_app.fragments

import androidx.lifecycle.ViewModel
import com.example.proyecto_final_oty_app.entities.Equipo
import com.example.proyecto_final_oty_app.entities.Personal
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class DetalleAsignacionViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    lateinit var idAsignacion:String
    lateinit var equipo:Equipo
    lateinit var personal: Personal


    private val firestore = FirebaseFirestore.getInstance()
    private val referenciaAsignaciones = firestore.collection("asignaciones")

    suspend fun eliminarAsignacion(idEquipo: String) = withContext(Dispatchers.IO) {
        val equipoDocument = referenciaAsignaciones.document(idEquipo)
        equipoDocument.delete().await()
    }
    suspend fun cambiarEstadoEquipo(idEquipo: String): Boolean = withContext(Dispatchers.IO) {
        val db = FirebaseFirestore.getInstance()
        val equipoRef = db.collection("equipos").document(idEquipo)
        val nuevoEstado="Disponible"
        val snapshot = equipoRef.get().await()

        return@withContext if (snapshot.exists()) {
            equipoRef.update("estado", "Disponible").await()
            true // Equipo encontrado y estado cambiado
        } else {
            false // Equipo no encontrado
        }
    }
}