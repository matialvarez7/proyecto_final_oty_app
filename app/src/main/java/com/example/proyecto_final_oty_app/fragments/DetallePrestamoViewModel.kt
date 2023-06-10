package com.example.proyecto_final_oty_app.fragments

import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_final_oty_app.adapters.AdapterDetallePrestamo
import com.example.proyecto_final_oty_app.adapters.AdapterPrestamo
import com.example.proyecto_final_oty_app.entities.Equipo
import com.example.proyecto_final_oty_app.entities.ItemPrestamo
import com.example.proyecto_final_oty_app.entities.Prestamo
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class DetallePrestamoViewModel : ViewModel() {

    lateinit var listadoEquiposDB : MutableList<Equipo>
    lateinit var listadoEquiposFinal : MutableList<Equipo>
    private var db = Firebase.firestore
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


}