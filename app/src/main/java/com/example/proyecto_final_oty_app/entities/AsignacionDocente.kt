package com.example.proyecto_final_oty_app.entities

import java.util.Date

data class AsignacionDocente(
    private var idPersona : String,
    private var idEquipo : String,
    private var fechaAsignacion : Date,
    private var fechaDevolucion : Date
)
