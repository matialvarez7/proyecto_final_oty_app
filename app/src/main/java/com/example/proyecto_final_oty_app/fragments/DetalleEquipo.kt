package com.example.proyecto_final_oty_app.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.proyecto_final_oty_app.R
import com.example.proyecto_final_oty_app.entities.Equipo
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Text


class DetalleEquipo : Fragment() {

    lateinit var v: View

    private lateinit var viewModel: DetalleEquipoViewModel
    lateinit var numInv: TextView
    lateinit var nomEquipo: TextView
    lateinit var numAnet: TextView
    lateinit var estado: TextView
    lateinit var editar:Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_detalle_equipo, container, false)
        numInv = v.findViewById(R.id.baseInventario)
        nomEquipo = v.findViewById((R.id.baseNombreEquipo))
        numAnet = v.findViewById(R.id.baseNumeroAnet)
        estado = v.findViewById(R.id.baseEstado)
        editar=v.findViewById(R.id.editar)
        return v
    }

    override fun onStart() {
        super.onStart()
        val equipo = DetalleEquipoArgs.fromBundle(requireArguments()).equipo
        numInv.text = equipo.inventario
        nomEquipo.text = equipo.nombre
        numAnet.text = equipo.anet
        estado.text = equipo.estado

        editar.setOnClickListener(){
            val action =DetalleEquipoDirections.actionDetalleEquipoToEditDetalleEquipo(equipo)
            findNavController().navigate(action)
        }
    }

}

