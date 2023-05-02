package com.example.proyecto_final_oty_app.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.proyecto_final_oty_app.R

class AsignacionDocente : Fragment() {

    companion object {
        fun newInstance() = AsignacionDocente()
    }

    private lateinit var viewModel: AsignacionDocenteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_asignacion_docente, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AsignacionDocenteViewModel::class.java)
        // TODO: Use the ViewModel
    }

}