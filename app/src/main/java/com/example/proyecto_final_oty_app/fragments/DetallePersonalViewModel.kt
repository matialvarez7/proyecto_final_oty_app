package com.example.proyecto_final_oty_app.fragments

import androidx.lifecycle.ViewModel
import com.example.proyecto_final_oty_app.entities.Personal
import com.example.proyecto_final_oty_app.entities.PersonalABM

class DetallePersonalViewModel : ViewModel() {
    private val personalABM = PersonalABM()

    suspend fun eliminarPersonal(idPersonal: String): Void? {

        return personalABM.eliminarPersonal(idPersonal)
    }
}