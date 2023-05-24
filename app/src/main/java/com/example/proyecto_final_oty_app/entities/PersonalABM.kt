package com.example.proyecto_final_oty_app.entities

import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class PersonalABM {
    private val firestore = FirebaseFirestore.getInstance()
    private val personalCollection = firestore.collection("personal")

    suspend fun agregarPersonal(personal: Personal): String? {
        val listaPersonal = personalCollection.document()

        val personalWithId = personal.copy(id = listaPersonal.id)

        listaPersonal.set(personalWithId).await()

        return listaPersonal.id
    }

    suspend fun existeDni(dni: String): Boolean {
        val dataPersonal = personalCollection.whereEqualTo("dni", dni).get().await()
        return !dataPersonal.isEmpty
    }

    suspend fun editarPersonal(personalId: String, personal: Personal) {
        personalCollection.document(personalId).set(personal).await()
    }

    suspend fun eliminarPersonal(idPersonal: String) = withContext(Dispatchers.IO) {
        val personalDocument = personalCollection.document(idPersonal)
        personalDocument.delete().await()
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
