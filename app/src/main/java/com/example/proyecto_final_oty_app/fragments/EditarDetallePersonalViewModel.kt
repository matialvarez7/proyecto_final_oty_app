package com.example.proyecto_final_oty_app.fragments

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.proyecto_final_oty_app.entities.Personal
import com.google.firebase.firestore.FirebaseFirestore

class EditarDetallePersonalViewModel : ViewModel() {
    // TODO: Implement the ViewModel

    private val firestore = FirebaseFirestore.getInstance()
    private val personalCollection = firestore.collection("personal")


    fun actualizarPersonal(personal: Personal) {

        personalCollection.document(personal.id!!)
            .set(personal)
            .addOnSuccessListener {
                Log.d(ContentValues.TAG, "Equipo actualizado correctamente")
            }
            .addOnFailureListener { e ->
                Log.w(ContentValues.TAG, "Error al actualizar equipo", e)
            }
    }
}