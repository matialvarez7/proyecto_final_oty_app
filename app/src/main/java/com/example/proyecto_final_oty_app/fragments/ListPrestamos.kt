package com.example.proyecto_final_oty_app.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_final_oty_app.R
import com.example.proyecto_final_oty_app.adapters.AdapterPrestamo
import com.example.proyecto_final_oty_app.entities.Equipo
import com.example.proyecto_final_oty_app.entities.Prestamo
import com.google.firebase.firestore.FirebaseFirestore

class ListPrestamos : Fragment() {

    companion object {
        fun newInstance() = ListPrestamos()
    }

    private lateinit var viewModel: ListPrestamosViewModel
    lateinit var v : View
    lateinit var recyclerPrestamos : RecyclerView
    lateinit var adapter : AdapterPrestamo
    lateinit var db : FirebaseFirestore
    lateinit var prestamosFinal : MutableList<Prestamo>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_list_prestamos, container, false)
        recyclerPrestamos = v.findViewById(R.id.recPrestamos)

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ListPrestamosViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onStart() {
        super.onStart()
        prestamosFinal = mutableListOf()
        viewModel.inicializarColecciones()
        viewModel.armarListaFinal()
        //adapter = AdapterPrestamo()
        recyclerPrestamos.layoutManager = LinearLayoutManager(context)
        recyclerPrestamos.adapter = adapter

    }

}