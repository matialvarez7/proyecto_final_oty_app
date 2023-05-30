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

class AsignacionDocente : Fragment() {

    companion object {
        fun newInstance() = AsignacionDocente()
    }



    private lateinit var viewModel: AsignacionDocenteViewModel
lateinit var v:View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v= inflater.inflate(R.layout.fragment_asignacion_docente, container, false)
    v=v.findViewById(R.id.nombreYapellido)
    return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AsignacionDocenteViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onStart() {
        super.onStart()

    }

}