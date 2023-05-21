package com.example.proyecto_final_oty_app.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.proyecto_final_oty_app.R

class DetallePersonal : Fragment() {

    lateinit var v : View
    lateinit var nombrePersonal : TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        v =  inflater.inflate(R.layout.fragment_detalle_personal, container, false)
        nombrePersonal = v.findViewById(R.id.nombrePersonalDetalle)


        return v
    }


    override fun onStart() {
        super.onStart()
        val personal = DetallePersonalArgs.fromBundle(requireArguments()).personal
        nombrePersonal.text = personal.nombre

    }

}

