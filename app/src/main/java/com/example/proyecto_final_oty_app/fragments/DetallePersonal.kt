package com.example.proyecto_final_oty_app.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.proyecto_final_oty_app.R

class DetallePersonal : Fragment() {

    lateinit var v : View
    lateinit var baseDNI : TextView
    lateinit var baseNombre : TextView
    lateinit var baseApellido : TextView
    lateinit var baseArea : TextView
    lateinit var editar: Button
    lateinit var eliminarBtn: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        v =  inflater.inflate(R.layout.fragment_detalle_personal, container, false)
        baseDNI = v.findViewById(R.id.baseDNI)
        baseNombre = v.findViewById(R.id.baseNombre)
        baseApellido = v.findViewById(R.id.baseApellido)
        baseArea = v.findViewById(R.id.baseArea)
        editar=v.findViewById(R.id.editar)
        eliminarBtn=v.findViewById(R.id.EliminarBtn)

        return v
    }


    override fun onStart() {
        super.onStart()
        val personal = DetallePersonalArgs.fromBundle(requireArguments()).personal
        baseDNI.text = personal.dni
        baseNombre.text = personal.nombre
        baseApellido.text = personal.apellido
        baseArea.text = personal.area
    }

}

