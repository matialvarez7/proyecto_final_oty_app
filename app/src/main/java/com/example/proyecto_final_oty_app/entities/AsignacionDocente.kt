package com.example.proyecto_final_oty_app.entities

import java.util.Date

data class AsignacionDocente(
    private var id : Int,
    private var idPersona : Int,
    private var idEquipo : Int,
    private var fechaAsignacion : Date,
    private var fechaDevolucion : Date
)
