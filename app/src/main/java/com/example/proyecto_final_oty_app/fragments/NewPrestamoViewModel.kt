package com.example.proyecto_final_oty_app.fragments

import androidx.lifecycle.ViewModel
import com.example.proyecto_final_oty_app.entities.Personal
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class NewPrestamoViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    val db = Firebase.firestore
    var idPrestamo : String = ""
    var idPersonal : String = ""
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
        var encontrado : Personal? = null
        var listAux : MutableList<Personal>
        var personalRef = db.collection("personal").whereEqualTo("dni", dni).get().await()
        if(!personalRef.isEmpty){
            listAux = personalRef.toObjects(Personal::class.java)
            for(e in listAux){
                encontrado = e
            }
        }
        return encontrado
    }

    fun setIdPrestamo() {
        val id = db.collection("personal").document()
        idPrestamo = id.id
    }

    fun getIdPrestamo() : String {
        return idPrestamo
    }

    fun setIdPersonal(id : String){
        idPersonal = id
    }
}