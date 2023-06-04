package com.example.proyecto_final_oty_app.fragments

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_final_oty_app.adapters.AdapterEquipo
import com.example.proyecto_final_oty_app.entities.Equipo
import com.example.proyecto_final_oty_app.entities.Personal
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.tasks.await

class ListEquipoViewModel : ViewModel() {

    lateinit var db : FirebaseFirestore
    lateinit var equipos : MutableList<Equipo>
    suspend fun obtenerColeccion () : MutableList<Equipo>{
        equipos = mutableListOf()
        this.db = FirebaseFirestore.getInstance()

        var baseEquipos = db.collection("equipos").orderBy("nombre").get().await()
        if(baseEquipos != null){
            equipos = baseEquipos.toObjects<Equipo>() as MutableList<Equipo>
        }
        return equipos
    }

}