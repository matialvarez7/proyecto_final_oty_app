package com.example.proyecto_final_oty_app.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import com.example.proyecto_final_oty_app.R
import com.google.android.material.snackbar.Snackbar

class NewEquipo : Fragment() {


    companion object {
        fun newInstance() = NewEquipo()
    }

    lateinit var v : View
    private lateinit var viewModel: NewEquipoViewModel
    lateinit var btnConfirmar : Button
    lateinit var nroInventario : EditText // recibe el número de inventario de la vista
    lateinit var nroEquipo : EditText // recibe el número de equipo de la vista
    lateinit var nroAnet : EditText // recibe el número de A-NET de la vista

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_new_equipo, container, false)

        // bindeamos los componentes de las vistas para poder usarlos
        nroInventario = v.findViewById(R.id.nroInventario)
        nroEquipo = v.findViewById(R.id.nroEquipo)
        nroAnet = v.findViewById(R.id.nroAnet)
        btnConfirmar = v.findViewById(R.id.btnConfirmar)

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NewEquipoViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onStart() {
        super.onStart()

        btnConfirmar.setOnClickListener {
            if(nroInventario.text.isEmpty()){
                Snackbar.make(v, "Completar el número de inventario", Snackbar.LENGTH_LONG).show()
            }
            if(nroEquipo.text.isEmpty()){
                Snackbar.make(v, "Completar el número de equipo", Snackbar.LENGTH_LONG).show()
            }
            if(nroAnet.text.isEmpty()){
                Snackbar.make(v, "Completar el número de A-NET", Snackbar.LENGTH_LONG).show()
            }

            viewModel.altaNuevoEquipo(nroInventario.text.toString(), nroEquipo.text.toString(), nroAnet.text.toString())

            nroInventario.text.clear()
            nroEquipo.text.clear()
            nroAnet.text.clear()
        }
    }

}