package com.example.proyecto_final_oty_app.fragments

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
    var personalACargo : Personal ? = null
    var listaItems : MutableList<ItemPrestamo> = arrayListOf()
    var listaEquipos : MutableList<Equipo> = arrayListOf()
    var auxListaEquipos : MutableList<Equipo> = arrayListOf()
    var liveListaEquipos : MutableLiveData<MutableList<Equipo>> = MutableLiveData()
    suspend fun confirmarPrestamo() : Boolean{
        var confirmado : Boolean = false
        lateinit var nuevoPrestamo : Prestamo
        try{
            this.crearItemsPrestamos(this.listaEquipos)
            this.setIdItemsPrestamo(this.listaItems)
            if(this.personalACargo != null && this.listaEquipos.isNotEmpty()){
                nuevoPrestamo = Prestamo(this.idPrestamo, this.personalACargo?.id.toString(), Date(), "Activo")
                db.collection("prestamos").document(idPrestamo).set(nuevoPrestamo).await()
                for(elemento in this.listaItems){
                    db.collection("itemsPrestamo").document(elemento.id).set(elemento).await()
                }
                for(equipo in this.listaEquipos){
                    db.collection("equipos").document(equipo.id).set(equipo).await()
                }
                confirmado =  true
                this.limpiarDatos()
            }
        }catch(e : Exception){
            Log.w(ContentValues.TAG, "Error: ", e)
        }
        return confirmado
    }

    suspend fun cancelarPrestamo() : Boolean{
        var cancelado : Boolean = false
        try{
            this.limpiarDatos()
            cancelado =  true
        }catch (e : Exception){
            Log.w(ContentValues.TAG, "Error:", e)
        }
        return cancelado
    }

    suspend fun confirmarEquipo() : Boolean{
        var confirmado : Boolean = false

        try{
            if (this.equipo != null){
                if (this.equipoDisponible()) {
                    this.equipo.estado = "En préstamo"
                    confirmado = true
                    listaEquipos.add(this.equipo)
                    this.actualizarDatos(this.listaEquipos)
                }
            }
        }catch (e : Exception){
            Log.w(ContentValues.TAG, "Error adding document", e)
        }
        return confirmado
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

    fun campoDniVacío(dni : String) : Boolean {
        var vacio : Boolean = false
        if(dni.isEmpty()) {
            vacio = true
        }
        return vacio
    }

    fun dniInvalido(dni: String) : Boolean {
        var invalido : Boolean = false
        if(dni.length > 8 || dni.length < 7){
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
    fun equipoDisponible(): Boolean {
        return this.equipo.estado.equals("Disponible", ignoreCase = true)
    }

    fun eliminarEquipoAniadido(posicion : Int) {
        this.listaEquipos.removeAt(posicion)
        this.actualizarDatos(this.listaEquipos)
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

    fun crearItemsPrestamos(equipos: MutableList<Equipo>) {
        for(eq in equipos){
            this.listaItems.add(ItemPrestamo("", eq.id, this.idPrestamo, "No devuelto"))
        }
    }
    fun mostrarNombrePersonal() : String {
        var apellido : String = ""
        if(this.personalACargo != null){
            apellido = this.personalACargo!!.apellido
        }
        return apellido
    }
    fun mostrarApellidoPersonal () : String {
        var nombre : String = ""
        if(this.personalACargo != null){
            nombre = this.personalACargo!!.nombre
        }
        return nombre

    }
    fun equipoYaAgregado(nroInventario : String) : Boolean {
        return this.listaEquipos.find { it.inventario == nroInventario } != null
    }
    fun limpiarDatos() {
        this.idPrestamo = ""
        this.listaEquipos.clear()
        this.listaItems.clear()
    }
    fun actualizarDatos(datos : MutableList<Equipo>){
        //this.auxListaEquipos = datos
        this.liveListaEquipos.value = this.listaEquipos
    }
}