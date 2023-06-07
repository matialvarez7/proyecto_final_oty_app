package com.example.proyecto_final_oty_app.fragments

import android.content.ContentValues
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.proyecto_final_oty_app.R
import com.example.proyecto_final_oty_app.entities.Personal
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.launch
import java.util.Locale
import kotlin.math.roundToInt

class Homepage : Fragment() {

    lateinit var v : View
    lateinit var txtTitle : TextView
    lateinit var btnAsigDocent : CardView
    lateinit var btnPrestamos : CardView
    lateinit var btnPersonal : CardView
    lateinit var btnEquipos : CardView
    lateinit var progreBarAsig : ProgressBar
    lateinit var progreBarPres : ProgressBar
    lateinit var db : FirebaseFirestore
    lateinit var asignaciones : MutableList<Personal>  //Cambiar por Asignaciones
    lateinit var EquiposAsignDocentes : MutableList<Personal> //Equipos de Asignaciones
    lateinit var prestamos : MutableList<Personal>  //Cambiar por Asignaciones
    lateinit var EquiposPrestamos : MutableList<Personal> //Equipos de Prestamos
    lateinit var cantAsign : TextView
    lateinit var cantPres : TextView
    lateinit var btnCerrarSesion: ImageButton
    lateinit var porcentAsignaciones : TextView
    lateinit var porcentPrestamos : TextView
    lateinit var viewModel: HomepageViewModel

          override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_homepage, container, false)
        txtTitle = v.findViewById(R.id.titleMenu)
        btnAsigDocent = v.findViewById(R.id.botonAsignacionesDocentes)
        btnPrestamos = v.findViewById(R.id.botonPrestamos)
        btnPersonal = v.findViewById(R.id.botonPersonal)
        btnEquipos = v.findViewById(R.id.botonEquipos)
        progreBarAsig = v.findViewById(R.id.progressBarAsig)
        progreBarPres = v.findViewById(R.id.progressBarPrestamos)
        cantAsign = v.findViewById(R.id.cantidadAsignaciones)
        cantPres = v.findViewById(R.id.cantidadPrestamos)
        btnCerrarSesion = v.findViewById(R.id.buttonCS)
        porcentAsignaciones = v.findViewById(R.id.porcentajeAsignaciones)
        porcentPrestamos = v.findViewById(R.id.porcentajePrestamos)

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomepageViewModel::class.java)
        // TODO: Use the ViewModel
    }

    // UNA VEZ TERMINADO PRESTAMOS Y ASIGNACIONES PASARLO AL VIEW MODEL.

    override fun onStart() {
        super.onStart()
        db = FirebaseFirestore.getInstance()
        asignaciones = mutableListOf()
        EquiposAsignDocentes = mutableListOf()
        prestamos = mutableListOf()
        EquiposPrestamos = mutableListOf()

        btnAsigDocent.setOnClickListener(){
            val action = HomepageDirections.actionHomepageToListAsignacionesDocentes()
            findNavController().navigate(action)
        }

        btnPrestamos.setOnClickListener(){
            val action = HomepageDirections.actionHomepageToListPrestamos()
            findNavController().navigate(action)
        }

        btnPersonal.setOnClickListener(){
            val action = HomepageDirections.actionHomepageToListPersonal()
            findNavController().navigate(action)
        }

        btnEquipos.setOnClickListener(){
            val action = HomepageDirections.actionHomepageToListEquipo2()
            findNavController().navigate(action)
        }

        btnCerrarSesion.setOnClickListener(){
            val action = HomepageDirections.actionHomepageToListEquipo2() //Modificar aca para que cierre sesion.
            findNavController().navigate(action)
        }

        lifecycleScope.launch {
            viewModel.generarProgressBar("Asignaciones",cantAsign, porcentAsignaciones, progreBarAsig)
            viewModel.generarProgressBar("Prestamos",cantPres, porcentPrestamos, progreBarPres)
        }

    }

}