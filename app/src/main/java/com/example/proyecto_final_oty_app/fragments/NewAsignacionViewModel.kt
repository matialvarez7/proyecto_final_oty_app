package com.example.proyecto_final_oty_app.fragments

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.proyecto_final_oty_app.entities.AsignacionDocente
import com.example.proyecto_final_oty_app.entities.Equipo
import com.example.proyecto_final_oty_app.entities.Personal
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await
import java.util.Date

class NewAsignacionViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    private val firestore = FirebaseFirestore.getInstance()
    private val personalCollection = firestore.collection("personal")
    private val equiposCollection = firestore.collection("equipos")
    private val asignacionesCollection = firestore.collection("asignaciones")
    suspend fun buscarPersonalByDNI(dni: String): Personal {
        val listaPersonalConDNI: MutableList<Personal> = mutableListOf()
        val dataPersonal = personalCollection.whereEqualTo("dni", dni).get().await()
        if (dataPersonal != null) {
            for (personal in dataPersonal) {
                listaPersonalConDNI.add(personal.toObject())// OBTENGO LA EQUIPO LIST
            }
        }
        when (listaPersonalConDNI.size) {
            1 -> {//INTERPRETO EL RESULTADO
                return listaPersonalConDNI[0]
            }

            0 -> {
                throw NoSuchElementException("No encontramos ningun personal con el dni: $dni")
            }

            else -> {
                throw IllegalStateException("Se encontraron mas de 1 registro con el dni: $dni por favor revise el apartado de personal")
            }
        }

    }

    suspend fun buscarEquipoByInventario(inventario: String): Equipo {
        val listaEquipoConInventario: MutableList<Equipo> = mutableListOf()
        val dataEquipo = equiposCollection.whereEqualTo("inventario", inventario).get().await()
        if (dataEquipo != null) {
            for (equipo in dataEquipo) {
                listaEquipoConInventario.add(equipo.toObject())// OBTENGO LA EQUIPO LIST
            }
        }
        when (listaEquipoConInventario.size) {//INTERPRETO EL RESULTADO
            1 -> {
                return listaEquipoConInventario[0]
            }

            0 -> {
                throw NoSuchElementException("No encontramos ningun equipo con el inventario: $inventario")
            }

            else -> {
                throw IllegalStateException("Se encontraron mas de 1 registro con el inventario: $inventario por favor revise el apartado de equipos")
            }
        }

    }

    suspend fun elEquipoFueAsignado(idEquipo: String): Boolean {
        var ok = false
        val dataAsignacionesActivas = asignacionesCollection.whereEqualTo("fechaDevolucion", null)
            .whereEqualTo("idEquipo", idEquipo).get().await()
        if (dataAsignacionesActivas.size() > 0) {
            ok = true
        }
        return ok
    }

    fun camposValidos(inventario: String, dni: String): Boolean {
        return inventario.isNotEmpty() && dni.isNotEmpty()
    }

    fun registrarAsignacion(
        idPersonalAsignar: String,
        idEquipoAsignar: String,
        fechaDeCreacion: Date
    ): Boolean {

        val id = asignacionesCollection.document()
        val nuevaAsignacion = AsignacionDocente(
            id = id.id,
            idPersonal = idPersonalAsignar,
            idEquipo = idEquipoAsignar,
            fechaAsignacion = fechaDeCreacion,
            fechaDevolucion = null
        )

        asignacionesCollection.document(id.id)
            .set(nuevaAsignacion)

        return true
    }
}
