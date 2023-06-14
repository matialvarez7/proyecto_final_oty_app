package com.example.proyecto_final_oty_app.fragments

import android.content.ContentValues
import android.util.Log
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_final_oty_app.adapters.AdapterPersonal
import com.example.proyecto_final_oty_app.entities.Personal
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.tasks.await
import java.util.Locale

class ListPersonalViewModel : ViewModel() {
    lateinit var db : FirebaseFirestore
    lateinit var personales : MutableList<Personal>

    suspend fun obtenerColeccion () : MutableList<Personal>{
        personales = mutableListOf()
        this.db = FirebaseFirestore.getInstance()

        var basePersonal = db.collection("personal").orderBy("dni").get().await()
        if(basePersonal != null){
            personales = basePersonal.toObjects<Personal>() as MutableList<Personal>
        }

        return personales.filter { it.estado=="Activo" } as MutableList<Personal>
    }


}


