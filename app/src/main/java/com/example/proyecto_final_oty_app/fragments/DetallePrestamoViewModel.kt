package com.example.proyecto_final_oty_app.fragments

import androidx.lifecycle.ViewModel
import com.example.proyecto_final_oty_app.entities.Equipo
import com.example.proyecto_final_oty_app.entities.ItemPrestamo
import com.example.proyecto_final_oty_app.entities.Prestamo
import com.example.proyecto_final_oty_app.entities.PrestamoFinal
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class DetallePrestamoViewModel : ViewModel() {

    lateinit var listadoEquiposDB : MutableList<Equipo>
    lateinit var listadoEquiposFinal : MutableList<Equipo>
    private var db = Firebase.firestore
    private val itemsPrestamoCollection = db.collection("itemsPrestamo")
    private val prestamosCollection = db.collection("prestamos")
    private val equiposCollection = db.collection("equipos")

    suspend fun obtenerEquipos(itemsPrestamo : MutableList<ItemPrestamo>) {
        //Obtener el listado de equipos de Firebase
        var baseEquipos = db.collection("equipos").get().await()
        listadoEquiposDB = mutableListOf()
        listadoEquiposFinal = mutableListOf()
        if (baseEquipos != null){ //si se obtuvo correctamente la lista de la DB, se convierte a objeto
            listadoEquiposDB = baseEquipos.toObjects<Equipo>() as MutableList<Equipo>
            //Recorrer los itemsPrestamo, encontrar su equipo asociado y guardarlo en el listado final a mandar al adapter.
            itemsPrestamo.forEach{itemPrestamo ->
                var equipoAux = listadoEquiposDB.find {equipo -> equipo.id == itemPrestamo.idEquipo}
                if (equipoAux != null){
                    listadoEquiposFinal.add(equipoAux)
                }
            }
        }
    }

    fun getListadoEquiposPrestamo(): MutableList<Equipo> {
        return listadoEquiposFinal
    }

    suspend fun finalizarPrestamo(prestamo: PrestamoFinal) = withContext(Dispatchers.IO) {
        val itemsPrestamoSnapshot = itemsPrestamoCollection.whereEqualTo("idPrestamo", prestamo.idPrestamo).get().await()

        val itemsPrestamo = itemsPrestamoSnapshot.toObjects(ItemPrestamo::class.java)

        // Marcar todos los itemsPrestamo como devueltos si no lo están ya.
        itemsPrestamo.forEach { itemPrestamo ->
            if(itemPrestamo.estadoItem != "devuelto") {
                val itemPrestamoDocument = itemsPrestamoCollection.document(itemPrestamo.id)
                itemPrestamoDocument.update("estadoItem", "devuelto").await()

                // También marcar el equipo asociado como disponible.
                val equipoDocument = equiposCollection.document(itemPrestamo.idEquipo)
                equipoDocument.update("estado", "disponible").await()
            }
        }

        // Marcar el prestamo como finalizado.
        val prestamoDocument = prestamosCollection.document(prestamo.idPrestamo)
        prestamoDocument.update("estadoPrestamo", "finalizado").await()
    }



}