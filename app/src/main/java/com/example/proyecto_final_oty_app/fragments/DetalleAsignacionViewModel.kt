package com.example.proyecto_final_oty_app.fragments

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.proyecto_final_oty_app.entities.Equipo
import com.example.proyecto_final_oty_app.entities.Personal
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class DetalleAsignacionViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    lateinit var equipo:Equipo
    lateinit var personal: Personal

    private val firestore = FirebaseFirestore.getInstance()
    private val referenciaAsignaciones = firestore.collection("asignaciones")

    suspend fun eliminarAsignacion(idEquipo: String) = withContext(Dispatchers.IO) {
        val equipoDocument = referenciaAsignaciones.document(idEquipo)
        equipoDocument.delete().await()
    }

}