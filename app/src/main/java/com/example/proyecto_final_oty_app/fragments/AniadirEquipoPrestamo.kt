package com.example.proyecto_final_oty_app.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.proyecto_final_oty_app.R

class AniadirEquipoPrestamo : Fragment() {

    companion object {
        fun newInstance() = AniadirEquipoPrestamo()
    }

    private lateinit var viewModel: AniadirEquipoPrestamoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_aniadir_equipo_prestamo, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AniadirEquipoPrestamoViewModel::class.java)
        // TODO: Use the ViewModel
    }

}