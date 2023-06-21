package com.example.proyecto_final_oty_app.fragments
import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.Lifecycling
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_final_oty_app.adapters.AdapterAsignacion
import com.example.proyecto_final_oty_app.entities.AsignacionDocente
import com.example.proyecto_final_oty_app.entities.Equipo
import com.example.proyecto_final_oty_app.entities.Personal
import com.example.proyecto_final_oty_app.entities.Prestamo
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import androidx.lifecycle.lifecycleScope

class ListAsignacionesDocentesViewModel : ViewModel() {

    var db : FirebaseFirestore = FirebaseFirestore.getInstance()
    var equipos : MutableList<Equipo> = mutableListOf()
    var asignaciones : MutableList<AsignacionDocente> = mutableListOf()
    var personales : MutableList<Personal> = mutableListOf()

    suspend fun equipos(){
        equipos = mutableListOf()
        var baseEquipo = db.collection("equipos").get().await()
        if (baseEquipo != null){
            equipos = baseEquipo.toObjects<Equipo>() as MutableList<Equipo>
        } else {
            Log.d(ContentValues.TAG, "Error getting documents")
        }
    }
    suspend fun personales(){
        personales = mutableListOf()
        var basePersonal = db.collection("personal").get().await()
        if (basePersonal != null){
            personales = basePersonal.toObjects<Personal>() as MutableList<Personal>
        } else {
            Log.d(ContentValues.TAG, "Error getting documents")
        }
    }

    suspend fun registro(){
        personales()
        equipos()
        asignaciones()

    }
    suspend fun asignaciones(){
        asignaciones = mutableListOf()
        var baseAsignacion = db.collection("asignaciones").get().await()
        if (baseAsignacion != null){
            asignaciones = baseAsignacion.toObjects<AsignacionDocente>() as MutableList<AsignacionDocente>
        } else {
            Log.d(ContentValues.TAG, "Error getting documents")
        }
    }

    fun buscarEquipoPos( equipoId: String): Int {
        var indice = 0
        var indiceUltimo = -1
        while (indice < equipos.size) {
            if (equipos[indice].id == equipoId) {
                return indice
            }
            indiceUltimo = indice
            indice++
        }
        return indiceUltimo
    }

    fun buscarPersonalPos( personaId: String): Int {
        var indice = 0
        var indiceUltimo = -1
        while (indice < personales.size) {
            if (personales[indice].id == personaId) {
                return indice
            }
            indiceUltimo = indice
            indice++
        }
        return indiceUltimo
    }

}

