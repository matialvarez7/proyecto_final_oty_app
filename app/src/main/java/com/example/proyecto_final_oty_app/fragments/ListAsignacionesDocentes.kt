package com.example.proyecto_final_oty_app.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.proyecto_final_oty_app.R

class ListAsignacionesDocentes : Fragment() {

    companion object {
        fun newInstance() = ListAsignacionesDocentes()
    }

    private lateinit var viewModel: ListAsignacionesDocentesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_asignaciones_docentes, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ListAsignacionesDocentesViewModel::class.java)
        // TODO: Use the ViewModel
    }

}