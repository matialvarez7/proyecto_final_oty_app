package com.example.proyecto_final_oty_app.fragments

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.proyecto_final_oty_app.entities.Equipo
import com.example.proyecto_final_oty_app.entities.ItemPrestamo
import com.example.proyecto_final_oty_app.entities.Personal
import com.example.proyecto_final_oty_app.entities.Prestamo
import com.example.proyecto_final_oty_app.entities.PrestamoFinal
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import java.sql.Date
import java.time.LocalDate

class ListPrestamosViewModel : ViewModel() {
    private var db = Firebase.firestore
    var prestamos : MutableList<Personal>  = mutableListOf()
    var personal : MutableList <Personal> = mutableListOf()
    var itemsPrestamo : MutableList <ItemPrestamo> = mutableListOf()
    var prestamoFinal : MutableList <PrestamoFinal> = mutableListOf()

    fun inicializarColecciones () {

        //Obtener toda la coleccion de préstamos
        db.collection("prestamos")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    prestamos.add(document.toObject())
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }

        //Obtener toda la coleccion de personal
        db.collection("personal")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    personal.add(document.toObject())
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }

        //Obtener toda la coleccion de itemsPrestamo
        db.collection("itemsPrestamo")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    itemsPrestamo.add(document.toObject())
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }

    }

    fun armarListaFinal() {
        var prestamoAux : Prestamo
        var personalAux : Personal
        var itemPrestamoAux : ItemPrestamo

        if (prestamos.isNotEmpty()) {
            for (prestamo in prestamos){
                var equiposAux : MutableList<Equipo> = mutableListOf()
                val pFinalAux : PrestamoFinal = PrestamoFinal("",
                    Date(2024, 1, 1),"","","","","",equiposAux)
            }
        }

    }


}