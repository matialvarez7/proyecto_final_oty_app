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
    lateinit var personalACargo : Personal
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

    suspend fun confirmarPrestamo(listaItems : MutableList<ItemPrestamo>) : Boolean{
        var confirmado : Boolean = false
        lateinit var nuevoPrestamo : Prestamo
        var fechaCreacion : Date
        try{
            this.setIdItemsPrestamo(listaItems)
            if(this.personalACargo != null){
                nuevoPrestamo = Prestamo(this.idPrestamo, this.personalACargo.id.toString(), "ahora", "Activo")
                db.collection("prestamos").document(idPrestamo).set(nuevoPrestamo).await()
                for(elemento in listaItems){
                    db.collection("itemsPrestamo").document(elemento.id).set(elemento).await()
                }
                this.idPrestamo = ""
                confirmado =  true
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

}