package com.example.proyecto_final_oty_app.fragments

import androidx.lifecycle.ViewModel
import com.example.proyecto_final_oty_app.entities.Personal
import com.example.proyecto_final_oty_app.entities.PersonalABM

class NewPersonalViewModel : ViewModel() {
    private val personalABM = PersonalABM()

    suspend fun agregarPersonal(personal: Personal): String {
        return personalABM.agregarPersonal(personal)
    }

    suspend fun existeDni(dni: String): Boolean {
        return personalABM.existeDni(dni)
    }

}