package com.example.proyecto_final_oty_app.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.proyecto_final_oty_app.R

class DetallePrestamoEquipo : Fragment() {

    companion object {
        fun newInstance() = DetallePrestamoEquipo()
    }

    private lateinit var viewModel: DetallePrestamoEquipoViewModel
    lateinit var v : View
    lateinit var inventario : TextView
    lateinit var nombre : TextView
    lateinit var anet : TextView
    lateinit var estado : TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_detalle_prestamo_equipo, container, false)
        inventario = v.findViewById(R.id.inventarioDetPrestEquipo)
        nombre = v.findViewById(R.id.nombreDetPrestEquipo)
        anet = v.findViewById(R.id.anetDetPrestEquipo)
        estado = v.findViewById(R.id.estadoDetPrestEquipo)
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetallePrestamoEquipoViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onStart() {
        super.onStart()
        val equipo = DetallePrestamoEquipoArgs.fromBundle(requireArguments()).equipo
        inventario.text = equipo.inventario
        nombre.text = equipo.nombre
        anet.text = equipo.anet
        estado.text = equipo.estado
    }

}