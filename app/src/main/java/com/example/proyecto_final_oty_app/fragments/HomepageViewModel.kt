package com.example.proyecto_final_oty_app.fragments

import android.content.ContentValues
import android.util.Log
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.ViewModel
import com.example.proyecto_final_oty_app.entities.Equipo
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.tasks.await
import kotlin.math.roundToInt

class HomepageViewModel : ViewModel() {

    lateinit var db : FirebaseFirestore

    suspend fun obtenerColeccionEquiposEnCurso(estado : String) : MutableList<Equipo> {

        var equiposEnCurso : MutableList<Equipo> = mutableListOf()

        this.db = FirebaseFirestore.getInstance()

        var baseEquipos = db.collection("equipos").whereEqualTo("estado", estado).get().await()

        if(baseEquipos != null){

            equiposEnCurso = baseEquipos.toObjects<Equipo>() as MutableList<Equipo>

        } else {

            Log.d(ContentValues.TAG, "Error getting documents")

        }

        return equiposEnCurso

    }

    suspend fun obtenerColeccionEquipos(nomeclaturaNet : String) : MutableList <Equipo>{

        var equipos : MutableList<Equipo>

        var equiposADevolver : MutableList<Equipo>  = mutableListOf()

        this.db = FirebaseFirestore.getInstance()

        var baseEquipos = db.collection("equipos").get().await()

        if(baseEquipos != null){

            equipos = baseEquipos.toObjects<Equipo>() as MutableList<Equipo>

            for (equipo in equipos){

                if(equipo.nombre.contains(nomeclaturaNet) && (!equipo.estado.equals("inactivo"))){
                    equiposADevolver.add(equipo)
                }
            }

        }

        return equiposADevolver
    }

    fun calcularPromedio(cantidad : Float, cantTotal : Float) : Float {

        var promedio = 0F

        if(cantTotal > 0) {

            promedio = (((cantidad / cantTotal) * 100))

        }

        return promedio

    }


    suspend fun generarProgressBar(tipoDeColeccion: String,textoCantidades: TextView, textPromedio: TextView, progreBarAsig : ProgressBar) {
        var promedio = 0F
        var cantEnCurso : Float
        var cantTotal : Float


        if(tipoDeColeccion == "Asignaciones"){

            cantTotal = obtenerColeccionEquipos("A-PROFH").size.toFloat()

            cantEnCurso = obtenerColeccionEquiposEnCurso("Asignado").size.toFloat()

            promedio = calcularPromedio(cantEnCurso, cantTotal)

            textoCantidades.text = "Equipos Asignados ${cantEnCurso.roundToInt()} / ${cantTotal.roundToInt()}"

        } else if(tipoDeColeccion == "Prestamos"){

            cantTotal = obtenerColeccionEquipos("A-NHG3").size.toFloat()

            cantEnCurso = obtenerColeccionEquiposEnCurso("En préstamo").size.toFloat()

            promedio = calcularPromedio(cantEnCurso, cantTotal)

            textoCantidades.text = "Equipos Prestados ${cantEnCurso.roundToInt()} / ${cantTotal.roundToInt()}"
        }


        progreBarAsig.setProgress(promedio.roundToInt())

        textPromedio.text = "${(promedio).roundToInt()} %"

    }


}