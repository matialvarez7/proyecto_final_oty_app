package com.example.proyecto_final_oty_app.fragments

import androidx.lifecycle.ViewModel
import com.example.proyecto_final_oty_app.entities.Personal
import com.example.proyecto_final_oty_app.entities.PersonalABM
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class DetallePersonalViewModel : ViewModel() {
    private val firestore = FirebaseFirestore.getInstance()
    private val personalCollection = firestore.collection("personal")


    suspend fun eliminarPersonal(idPersonal : String) = withContext(Dispatchers.IO) {
        val personalDocument = personalCollection.document(idPersonal)
        personalDocument.delete().await()
    }

}