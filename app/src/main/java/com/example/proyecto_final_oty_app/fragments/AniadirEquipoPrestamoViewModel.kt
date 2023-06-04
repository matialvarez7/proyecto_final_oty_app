package com.example.proyecto_final_oty_app.fragments

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.proyecto_final_oty_app.entities.Equipo
import com.example.proyecto_final_oty_app.entities.ItemPrestamo
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class AniadirEquipoPrestamoViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    val db = Firebase.firestore
    lateinit var equipo: Equipo
    var listaItems : MutableList<ItemPrestamo> = arrayListOf()
    var listaEquipos : MutableList<Equipo> = arrayListOf()
    fun campoInventarioVacío(inventario: String): Boolean {
        var vacio: Boolean = false
        if (inventario.isEmpty()) {
            vacio = true
        }
        return vacio
    }

    suspend fun buscarEquipo(inventario : String): Boolean {
        var encontrado = false
        var listAux: MutableList<Equipo>

        try{
            var equipoRef = db.collection("equipos").whereEqualTo("inventario", inventario).get().await()
            if (!equipoRef.isEmpty) {
                listAux = equipoRef.toObjects(Equipo::class.java)
                for (e in listAux) {
                    this.equipo = e
                    encontrado = true
                }
            }
        }catch (e : Exception){

        }
        return encontrado
    }

    suspend fun confirmarEquipo(idPrestamo : String) : Boolean{
        var confirmado : Boolean = false
        lateinit var item : ItemPrestamo

        try{
            if (this.equipo != null){
                if (this.equipoDisponible()) {
                        item = ItemPrestamo("", this.equipo.id, idPrestamo, "No devuelto")
                        listaItems.add(item)
                        listaEquipos.add(this.equipo)
                        this.equipo.estado = "En préstamo"
                        db.collection("equipos").document(this.equipo.id).set(this.equipo).await()
                        confirmado = true
                }
            }
        }catch (e : Exception){
            Log.w(ContentValues.TAG, "Error adding document", e)
        }
        return confirmado
    }

    fun equipoYaAgregado(equipo : Equipo) : Boolean {
        return this.listaItems.find { it.idEquipo == equipo.id } != null
    }
    fun traerEquipos() : MutableList<ItemPrestamo> {
        return this.listaItems
    }

    fun limpiarListaEquipos() {
        this.listaEquipos.clear()
    }

    fun limpiarListaItemsPrestamo() {
        this.listaItems.clear()
    }
    fun equipoDisponible(): Boolean {
        return this.equipo.estado.equals("Disponible", ignoreCase = true)
    }
}