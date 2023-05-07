package com.example.proyecto_final_oty_app.entities

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.tasks.await

class PersonalABM {
    private val firestore = FirebaseFirestore.getInstance()
    private val personalCollection = firestore.collection("personal")

    suspend fun agregarPersonal(personal: Personal) {
        try {
            personalCollection.add(personal).await()
        } catch (e: Exception) {
            Log.e("PersonalABM", "Error al agregar personal", e)
        }
    }

    suspend fun editarPersonal(personalId: String, personal: Personal) {
        personalCollection.document(personalId).set(personal).await()
    }

    suspend fun eliminarPersonal(personalId: String) {
        personalCollection.document(personalId).delete().await()
    }

    suspend fun listarPersonal(): List<Personal> {
        val snapshot: QuerySnapshot = personalCollection.get().await()
        return snapshot.toObjects(Personal::class.java)
    }

    suspend fun obtenerPersonal(personalId: String): Personal? {
        val documentSnapshot: DocumentSnapshot = personalCollection.document(personalId).get().await()
        return if (documentSnapshot.exists()) {
            documentSnapshot.toObject(Personal::class.java)?.copy(id = documentSnapshot.id)
        } else {
            null
        }
    }

}
