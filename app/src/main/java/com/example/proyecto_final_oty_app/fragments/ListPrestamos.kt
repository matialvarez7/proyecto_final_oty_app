package com.example.proyecto_final_oty_app.fragments

import android.content.ContentValues
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_final_oty_app.R
import com.example.proyecto_final_oty_app.adapters.AdapterPrestamo
import com.example.proyecto_final_oty_app.entities.Equipo
import com.example.proyecto_final_oty_app.entities.ItemPrestamo
import com.example.proyecto_final_oty_app.entities.Prestamo
import com.example.proyecto_final_oty_app.entities.PrestamoFinal
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.launch
import java.sql.Date

class ListPrestamos : Fragment() {

    companion object {
        fun newInstance() = ListPrestamos()
    }

    private lateinit var viewModel: ListPrestamosViewModel
    lateinit var v : View
    lateinit var recyclerPrestamos : RecyclerView
    lateinit var adapter : AdapterPrestamo

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

        lifecycleScope.launch {
         viewModel.inicializarColecciones()
         viewModel.armarListaFinal()
         var listaFinal = viewModel.prestamoFinal
         adapter = AdapterPrestamo(listaFinal){ position ->
                val action = ListPrestamosDirections.actionListPrestamosToDetallePrestamo(listaFinal[position])
                findNavController().navigate(action)
            }
            recyclerPrestamos.layoutManager = LinearLayoutManager(context)
            recyclerPrestamos.adapter = adapter
        }

    }

}