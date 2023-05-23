package com.example.proyecto_final_oty_app.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.proyecto_final_oty_app.R

class Homepage : Fragment() {

     lateinit var v : View
    lateinit var txtTitle : TextView
    lateinit var btnAsigDocent : Button
    lateinit var btnPrestamos : Button
    lateinit var btnPersonal : Button
    lateinit var btnEquipos : Button
    //lateinit var btnCerrarSesion: Button - IMPLEMENTAR


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_homepage, container, false)
        txtTitle = v.findViewById(R.id.titleMenu)
        btnAsigDocent = v.findViewById(R.id.buttonAsigDoc)
        btnPrestamos = v.findViewById(R.id.buttonPrestamos)
        btnPersonal = v.findViewById(R.id.buttonPersonal)
        btnEquipos = v.findViewById(R.id.buttonEquipos)
        //btnCerrarSesion = v.findViewById(R.id.buttonCS)


        return v
    }

    override fun onStart() {
        super.onStart()

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




    }

}


