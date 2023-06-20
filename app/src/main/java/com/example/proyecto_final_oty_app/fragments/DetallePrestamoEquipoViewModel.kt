package com.example.proyecto_final_oty_app.fragments

import androidx.lifecycle.ViewModel
import com.example.proyecto_final_oty_app.entities.Equipo
import com.example.proyecto_final_oty_app.entities.ItemPrestamo
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class DetallePrestamoEquipoViewModel : ViewModel() {
    private val firestore = FirebaseFirestore.getInstance()
    private val itemsPrestamoCollection = firestore.collection("itemsPrestamo")
    private val equiposCollection = firestore.collection("equipos")

    suspend fun obtenerItemPrestamoPorIdEquipo(idEquipo: String) = withContext(Dispatchers.IO) {
        val snapshot = itemsPrestamoCollection
            .whereEqualTo("idEquipo", idEquipo)
            .get()
            .await()

        if (!snapshot.isEmpty) {
            // Esto devuelve el primer ItemPrestamo que coincide.
            return@withContext snapshot.documents[0].toObject(ItemPrestamo::class.java)
        } else {
            throw Exception("No se encontró ItemPrestamo para el idEquipo proporcionado.")
        }
    }

    suspend fun devolverEquipoYMarcarItemPrestamo(idItemPrestamo: String, idEquipo: String) = withContext(Dispatchers.IO) {
        val itemPrestamoDocument = itemsPrestamoCollection.document(idItemPrestamo)
        val equipoDocument = equiposCollection.document(idEquipo)

        // Primero obtengo el documento ItemPrestamo y cambia el estado a "devuelto"
        try {
            itemPrestamoDocument.update("estadoItem", "Devuelto").await()
        } catch (e: Exception) {
            throw e
        }

        //Cambio el estado del Equipo a "disponible"
        try {
            val snapshot = equipoDocument.get().await()
            val equipo = snapshot.toObject(Equipo::class.java)
            equipo?.let {
                it.estado = "Disponible"
                equipoDocument.set(it).await()
            }
        } catch (e: Exception) {
            throw e
        }
    }
}
