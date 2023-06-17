package com.example.proyecto_final_oty_app.fragments

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.proyecto_final_oty_app.entities.Personal
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import com.example.proyecto_final_oty_app.entities.PersonalABM

class DetallePersonalViewModel : ViewModel() {
    private val firestore = FirebaseFirestore.getInstance()
    private val personalCollection = firestore.collection("personal")


    suspend fun eliminarPersonal(idPersonal: String?) = withContext(Dispatchers.IO) {
        if (idPersonal != null) {
            val personalDocument = personalCollection.document(idPersonal)
            personalDocument.update("estado", "Inactivo").await()
        }
    }
}