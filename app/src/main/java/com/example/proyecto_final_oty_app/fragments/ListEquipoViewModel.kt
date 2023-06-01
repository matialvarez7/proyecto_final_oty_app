package com.example.proyecto_final_oty_app.fragments

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_final_oty_app.adapters.AdapterEquipo
import com.example.proyecto_final_oty_app.entities.Equipo
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

class ListEquipoViewModel : ViewModel() {

    lateinit var db : FirebaseFirestore
    fun mostrarColeccion(lista : MutableList<Equipo>, recyclerPersonal : RecyclerView, adapter : AdapterEquipo){

        this.db = FirebaseFirestore.getInstance()

        db.collection("equipos")
            .get()
            .addOnSuccessListener { snapshot ->
                if (snapshot != null) {
                    for (personal in snapshot) {
                        lista.add(personal.toObject())
                    }

                    recyclerPersonal.adapter = adapter
                }
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents: ", exception)
            }
    }

}