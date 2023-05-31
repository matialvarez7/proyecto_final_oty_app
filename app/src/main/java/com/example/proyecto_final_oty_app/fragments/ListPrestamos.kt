package com.example.proyecto_final_oty_app.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    lateinit var db : FirebaseFirestore
    lateinit var prestamosFinal : MutableList<PrestamoFinal>

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

        /*
        var itemsPrueba : MutableList<ItemPrestamo> = mutableListOf()
        itemsPrueba.add(ItemPrestamo("1","12345","111","Disponible"))
        itemsPrueba.add(ItemPrestamo("2","54321","222","Disponible"))
        itemsPrueba.add(ItemPrestamo("3","54","2223333","No disponible"))
        prestamosFinal = mutableListOf()
        prestamosFinal.add(PrestamoFinal("1", Date(2024, 1, 1),"En curso","pepe","papito",itemsPrueba))
        prestamosFinal.add(PrestamoFinal("2", Date(2024, 1, 1),"Finalizado","sandra","saraza",itemsPrueba))
         */

        lifecycleScope.launch {
            viewModel.inicializarColecciones()
        }

        viewModel.armarListaFinal()
        val listaPrestamoFinal = viewModel.getListaFinal()

        adapter = AdapterPrestamo(listaPrestamoFinal) /* { position ->
                 val action = ListPrestamosDirections.actionListPrestamosToDetallePrestamo()
                 findNavController().navigate(action)
        }*/
        recyclerPrestamos.layoutManager = LinearLayoutManager(context)
        recyclerPrestamos.adapter = adapter

    }

}