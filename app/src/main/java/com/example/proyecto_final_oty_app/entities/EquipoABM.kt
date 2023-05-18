package com.example.proyecto_final_oty_app.entities

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class EquipoABM {
    private val firestore = FirebaseFirestore.getInstance()
    private val equiposCollection = firestore.collection("equipos")


    suspend fun eliminarEquipo(idEquipo: String) = withContext(Dispatchers.IO) {
        val equipoDocument = equiposCollection.document(idEquipo)
        equipoDocument.delete().await()
    }

}