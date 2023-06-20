package com.example.proyecto_final_oty_app.entities

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

    suspend fun eliminarPersonal(idPersonal: String) = withContext(Dispatchers.IO) {
        val personalDocument = personalCollection.document(idPersonal)
        personalDocument.delete().await()
    }

}
