package com.example.proyecto_final_oty_app.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.proyecto_final_oty_app.R

class ListPrestamos : Fragment() {

    /*companion object {
        fun newInstance() = ListPrestamos()
    }*/

    lateinit var v : View
    lateinit var nuevoPrestamo : Button

    //private lateinit var viewModel: ListPrestamosViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_list_prestamos, container, false)
        nuevoPrestamo = v.findViewById(R.id.nuevoPrestamo)
        return v
    }

    /*override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ListPrestamosViewModel::class.java)
        // TODO: Use the ViewModel
    }*/

    override fun onStart() {
        super.onStart()
        nuevoPrestamo.setOnClickListener(){
            val a = ListPrestamosDirections.actionListPrestamosToNewPrestamo()
            findNavController().navigate(a)
        }
    }

}