package com.example.proyecto_final_oty_app.fragments

import android.content.ContentValues
import android.util.Log
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import com.example.proyecto_final_oty_app.entities.Equipo
import com.example.proyecto_final_oty_app.entities.ItemPrestamo
import com.example.proyecto_final_oty_app.entities.Personal
import com.example.proyecto_final_oty_app.entities.Prestamo
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlin.math.roundToInt

class HomepageViewModel : ViewModel() {

    lateinit var db : FirebaseFirestore
    lateinit var equipos : MutableList<Equipo>
    lateinit var asignaciones : MutableList<AsignacionDocente>
    lateinit var prestamos : MutableList<Equipo>

    suspend fun obtenerColeccionAsignacionDocenteEnCurso() : MutableList<AsignacionDocente> {
        asignaciones = mutableListOf()
        this.db = FirebaseFirestore.getInstance()

        var baseAsignaciones = db.collection("asignaciones").get().await()
        if(baseAsignaciones != null){
            asignaciones = baseAsignaciones.toObjects<AsignacionDocente>() as MutableList<AsignacionDocente>
        } else {
            Log.d(ContentValues.TAG, "Error getting documents")
        }
        return asignaciones
    }

    suspend fun obtenerColeccionPrestamosEnCurso() : MutableList<Equipo> {
        prestamos = mutableListOf()
        this.db = FirebaseFirestore.getInstance()

        var basePrestamos = db.collection("equipos").whereEqualTo("estado","En préstamo").get().await()
        if (basePrestamos != null){
            prestamos = basePrestamos.toObjects<Equipo>() as MutableList<Equipo>
        } else {
            Log.d(ContentValues.TAG, "Error getting documents")
        }
        return prestamos
    }

    suspend fun obtenerColeccionEquipos(tipoDeColeccion: String) : MutableList<Equipo>{
        equipos = mutableListOf()
        var equiposADevolver : MutableList<Equipo>  = mutableListOf()

        this.db = FirebaseFirestore.getInstance()

        var baseEquipos = db.collection("equipos").get().await()

        if(baseEquipos != null){
            equipos = baseEquipos.toObjects<Equipo>() as MutableList<Equipo>

            if(tipoDeColeccion == "Asignaciones"){

                for (equipo in equipos){

                    if(equipo.nombre.contains("A-PROFH")){
                        equiposADevolver.add(equipo)
                    }
                }

            } else if (tipoDeColeccion == "Prestamos"){

                for (equipo in equipos){

                    if(equipo.nombre.contains("A-NHG3-OTYP")){
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
            cantEnCurso = obtenerColeccionAsignacionDocenteEnCurso().size.toFloat()
            promedio =(((cantEnCurso/cantTotal)* 100))
            textoCantidades.text = "Cantidad Asignada ${cantEnCurso.roundToInt()} / ${cantTotal.roundToInt()}"
        } else if(tipoDeColeccion == "Prestamos" && cantTotal > 0){
            cantEnCurso =obtenerColeccionPrestamosEnCurso().size.toFloat()
            promedio =(((cantEnCurso/cantTotal)* 100))
            textoCantidades.text = "Cantidad Prestada ${cantEnCurso.roundToInt()} / ${cantTotal.roundToInt()}"
        }


        progreBarAsig.setProgress(promedio.roundToInt())

        textPromedio.text = "${(promedio).roundToInt()} %"

    }

}