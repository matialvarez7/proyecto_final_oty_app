package com.example.proyecto_final_oty_app.fragments


import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_final_oty_app.R
import com.example.proyecto_final_oty_app.adapters.AdapterEquipo
import com.example.proyecto_final_oty_app.adapters.AdapterPrestamo
import com.example.proyecto_final_oty_app.entities.Equipo
import com.example.proyecto_final_oty_app.entities.PrestamoFinal

import kotlinx.coroutines.launch
import java.util.Locale


class ListPrestamos : Fragment() {

    companion object {
        fun newInstance() = ListPrestamos()
    }

    private lateinit var viewModel: ListPrestamosViewModel
    lateinit var v : View
    lateinit var recyclerPrestamos : RecyclerView
    lateinit var adapter : AdapterPrestamo
    lateinit var buscadorPrestamos : SearchView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_list_prestamos, container, false)
        recyclerPrestamos = v.findViewById(R.id.recPrestamos)
        buscadorPrestamos = v.findViewById(R.id.searchViewPrestamos)
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ListPrestamosViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onStart() {
        super.onStart()
        var listaFinal : MutableList<PrestamoFinal> = mutableListOf()

        listaFinal.clear()



        lifecycleScope.launch {
            recyclerPrestamos.setHasFixedSize(true)
            recyclerPrestamos.layoutManager = LinearLayoutManager(context)
            viewModel.inicializarColecciones()
            viewModel.armarListaFinal()
            listaFinal = viewModel.prestamoFinal
            adapter = AdapterPrestamo(listaFinal){ position ->
                val action = ListPrestamosDirections.actionListPrestamosToDetallePrestamo(listaFinal[position])
                findNavController().navigate(action)
            }

            recyclerPrestamos.adapter = adapter
        }

        buscadorPrestamos.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }

            fun filterList(query : String?){
                if(query != null){
                    val filteredList = mutableListOf<PrestamoFinal>()
                    for (prestamo in listaFinal){
                        if(prestamo.nombre.lowercase().contains(query) || prestamo.nombre.contains(query)
                            || prestamo.apellido.contains(query) || prestamo.apellido.lowercase().contains(query) ){
                            filteredList.add(prestamo)
                        }
                    }


                        adapter = AdapterPrestamo(filteredList){
                                position -> val action = ListPrestamosDirections.actionListPrestamosToDetallePrestamo(filteredList[position])
                            findNavController().navigate(action)
                        }
                        recyclerPrestamos.adapter = adapter


                }

            }

        })

    }



}