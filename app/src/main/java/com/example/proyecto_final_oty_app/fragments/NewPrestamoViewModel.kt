package com.example.proyecto_final_oty_app.fragments

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.proyecto_final_oty_app.entities.Equipo
import com.example.proyecto_final_oty_app.entities.ItemPrestamo
import com.example.proyecto_final_oty_app.entities.Personal
import com.example.proyecto_final_oty_app.entities.Prestamo
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class NewPrestamoViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    val db = Firebase.firestore
    var idPrestamo : String = ""
    lateinit var equipo: Equipo
    lateinit var personalACargo : Personal
    var listaItems : MutableList<ItemPrestamo> = arrayListOf()
    var listaEquipos : MutableList<Equipo> = arrayListOf()
    fun campoDniVacío(dni : String) : Boolean {
        var vacio : Boolean = false
        if(dni.isEmpty()) {
            vacio = true
        }
        return vacio
    }

    fun dniInvalido(dni: String) : Boolean {
        var invalido : Boolean = false
        if(dni.length > 8){
            invalido = true
        }
        return invalido
    }

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

    suspend fun buscarResponsable(dni : String) : Personal? {
        var listAux : MutableList<Personal>
        var personalRef = db.collection("personal").whereEqualTo("dni", dni).get().await()
        if(!personalRef.isEmpty){
            listAux = personalRef.toObjects(Personal::class.java)
            for(e in listAux){
                this.personalACargo = e
            }
        }
        return this.personalACargo
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

    fun equipoDisponible(): Boolean {
        return this.equipo.estado.equals("Disponible", ignoreCase = true)
    }
    suspend fun confirmarPrestamo() : Boolean{
        var confirmado : Boolean = false
        lateinit var nuevoPrestamo : Prestamo
        try{
            this.setIdItemsPrestamo(this.listaItems)
            if(this.personalACargo != null){
                nuevoPrestamo = Prestamo(this.idPrestamo, this.personalACargo.id.toString(), Date(), "Activo")
                db.collection("prestamos").document(idPrestamo).set(nuevoPrestamo).await()
                for(elemento in listaItems){
                    db.collection("itemsPrestamo").document(elemento.id).set(elemento).await()
                }
                confirmado =  true
                this.idPrestamo = ""
                this.limpiarListaEquipos()
                this.limpiarListaItemsPrestamo()
            }
        }catch(e : Exception){
            for(elemento in listaItems){
                db.collection("equipos").document(elemento.idEquipo).update("estado", "Disponible").await()
            }
        }
        return confirmado
    }

    fun setIdPrestamo() {
        if(this.idPrestamo.isEmpty()){
            val id = db.collection("personal").document()
            idPrestamo = id.id
        }
    }

    fun setIdItemsPrestamo(lista : MutableList<ItemPrestamo>){
        for(elemento in lista) {
            val idItemsPrestamo = db.collection("itemsPrestamo").document()
            elemento.id = idItemsPrestamo.id
        }
    }
    fun mostrarNombrePersonal() : String {
        return this.personalACargo.nombre
    }

    fun mostrarApellidoPersonal () : String {
       return this.personalACargo.apellido
    }
    fun equipoYaAgregado(equipo : Equipo) : Boolean {
        return this.listaItems.find { it.idEquipo == equipo.id } != null
    }

    fun limpiarListaEquipos() {
        this.listaEquipos.clear()
    }

    fun limpiarListaItemsPrestamo() {
        this.listaItems.clear()
    }
}