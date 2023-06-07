package com.example.proyecto_final_oty_app.fragments

import android.content.ContentValues
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_final_oty_app.R
import com.example.proyecto_final_oty_app.adapters.AdapterEquipo
import com.example.proyecto_final_oty_app.adapters.AdapterPersonal
import com.example.proyecto_final_oty_app.entities.Equipo
import com.example.proyecto_final_oty_app.entities.Personal
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.launch
import java.util.Locale

class ListEquipo : Fragment() {

    lateinit var v : View
    lateinit var recyclerEquipos : RecyclerView
    lateinit var adapter : AdapterEquipo
    lateinit var equipos : MutableList<Equipo>
    lateinit var txtTitle : TextView
    lateinit var btnAlta : Button
    lateinit var viewModel: ListEquipoViewModel
    lateinit var buscadorEquipos : SearchView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_list_equipo, container, false)
        recyclerEquipos = v.findViewById(R.id.ListaEquipos)
        txtTitle = v.findViewById(R.id.titleListaEquipos)
        btnAlta = v.findViewById(R.id.bottonAregarEquipos)
        buscadorEquipos = v.findViewById(R.id.searchViewEquipos)

      return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ListEquipoViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onStart() {
        super.onStart()
        equipos = mutableListOf()

        equipos.clear()


        lifecycleScope.launch {
            equipos = viewModel.obtenerColeccion()

            recyclerEquipos.setHasFixedSize(true)

            recyclerEquipos.layoutManager = LinearLayoutManager(context)

            adapter = AdapterEquipo(equipos){
                    position -> val action =ListEquipoDirections.actionListEquipoToDetalleEquipo(equipos[position])
                findNavController().navigate(action)
            }

            recyclerEquipos.adapter = adapter
        }


        buscadorEquipos.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }

            fun filterList(query : String?){
                if(query != null){
                    val filteredList = mutableListOf<Equipo>()
                    for ( equipo in equipos){
                        if(equipo.nombre.lowercase(Locale.ROOT).contains(query) || equipo.nombre.contains(query)){
                            filteredList.add(equipo)
                        }
                    }

                    if(!filteredList.isEmpty()){

                        adapter = AdapterEquipo(filteredList){
                                position -> val action =ListEquipoDirections.actionListEquipoToDetalleEquipo(equipos[position])
                            findNavController().navigate(action)
                        }
                        recyclerEquipos.adapter = adapter

                    }
                }

            }

        })



        btnAlta.setOnClickListener(){
            val action = ListEquipoDirections.actionListEquipoToNewEquipo()
            findNavController().navigate(action)
        }


    }

}





