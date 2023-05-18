package com.example.proyecto_final_oty_app.fragments

import androidx.lifecycle.ViewModel
import com.example.proyecto_final_oty_app.entities.EquipoABM
import com.example.proyecto_final_oty_app.entities.PersonalABM

class DetalleEquipoViewModel : ViewModel() {
    private val equipoABM = EquipoABM()

    suspend fun eliminarEquipo(idEquipo: String): Void? {
        return equipoABM.eliminarEquipo(idEquipo)
    }
}