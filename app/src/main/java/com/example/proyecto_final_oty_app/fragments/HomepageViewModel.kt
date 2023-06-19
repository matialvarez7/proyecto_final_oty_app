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



    suspend fun obtenerColeccionEquipos(tipoDeColeccion: String) : MutableList <Equipo>{

        var equipos : MutableList<Equipo>

        var equiposADevolver : MutableList<Equipo>  = mutableListOf()

        this.db = FirebaseFirestore.getInstance()

        var baseEquipos = db.collection("equipos").get().await()

        if(baseEquipos != null){

            equipos = baseEquipos.toObjects<Equipo>() as MutableList<Equipo>

            if(tipoDeColeccion == "Asignaciones"){

                for (equipo in equipos){

                    if(equipo.nombre.contains("A-PROFH") && (!equipo.estado.equals("inactivo"))){
                        equiposADevolver.add(equipo)
                    }
                }

            } else if (tipoDeColeccion == "Prestamos"){

                for (equipo in equipos){

                    if(equipo.nombre.contains("A-NHG3-OTYP") && (!equipo.estado.equals("inactivo"))){
                        equiposADevolver.add(equipo)
                    }
                }
            }
        }

        return equiposADevolver
    }

    suspend fun generarProgressBar(tipoDeColeccion: String,textoCantidades: TextView, textPromedio: TextView, progreBarAsig : ProgressBar) {
        var promedio : Float = 0F
        var cantEnCurso : Float = 0F
        var cantTotal : Float = obtenerColeccionEquipos(tipoDeColeccion).size.toFloat()


        if(tipoDeColeccion == "Asignaciones" && cantTotal > 0){
            cantEnCurso = obtenerColeccionEquiposEnCurso("Asignado").size.toFloat()
            promedio =(((cantEnCurso/cantTotal)* 100))
            textoCantidades.text = "Cantidad Asignada ${cantEnCurso.roundToInt()} / ${cantTotal.roundToInt()}"
        } else if(tipoDeColeccion == "Prestamos" && cantTotal > 0){
            cantEnCurso = obtenerColeccionEquiposEnCurso("En préstamo").size.toFloat()
            promedio =(((cantEnCurso/cantTotal)* 100))
            textoCantidades.text = "Cantidad Prestada ${cantEnCurso.roundToInt()} / ${cantTotal.roundToInt()}"
        }


        progreBarAsig.setProgress(promedio.roundToInt())

        textPromedio.text = "${(promedio).roundToInt()} %"

    }

}