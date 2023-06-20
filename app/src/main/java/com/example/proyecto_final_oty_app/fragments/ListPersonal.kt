package com.example.proyecto_final_oty_app.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_final_oty_app.R
import com.example.proyecto_final_oty_app.adapters.AdapterPersonal
import com.example.proyecto_final_oty_app.entities.Personal
import kotlinx.coroutines.launch
import java.util.Locale

class ListPersonal : Fragment() {


    companion object {
        fun newInstance() = ListPrestamos()
    }

    lateinit var v : View
    lateinit var recyclerPersonal : RecyclerView
    lateinit var adapter : AdapterPersonal
    lateinit var personales : MutableList<Personal>
    lateinit var txtTitle : TextView
    lateinit var btnAlta : Button
    lateinit var viewModel: ListPersonalViewModel
    lateinit var buscadorPersonal : SearchView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_list_personal, container, false)
        recyclerPersonal = v.findViewById(R.id.ListaPersonal)
        txtTitle = v.findViewById(R.id.titleListaPersonal)
        btnAlta = v.findViewById(R.id.bottonAregarPersonal)
        buscadorPersonal = v.findViewById(R.id.searchViewPersonal)

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ListPersonalViewModel::class.java)
        // TODO: Use the ViewModel
    }



    override fun onStart() {
        super.onStart()
        personales = mutableListOf()

        personales.clear()



        lifecycleScope.launch {
            personales = viewModel.obtenerColeccion()

            recyclerPersonal.setHasFixedSize(true)

            recyclerPersonal.layoutManager = LinearLayoutManager(context)

            adapter = AdapterPersonal(personales){
                    position -> val action =ListPersonalDirections.actionListPersonalToDetallePersonal(personales[position])
                findNavController().navigate(action)
            }

            recyclerPersonal.adapter = adapter
        }


        buscadorPersonal.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }

            fun filterList(query : String?){
                if(query != null){
                    val filteredList = mutableListOf<Personal>()
                    for ( personal in personales){
                        val lowercaseQuery = query.lowercase(Locale.ROOT)
                        if(personal.apellido.lowercase(Locale.ROOT).contains(lowercaseQuery)
                            || personal.nombre.lowercase(Locale.ROOT).contains(lowercaseQuery)
                            || personal.dni.lowercase(Locale.ROOT).contains(lowercaseQuery)){
                            filteredList.add(personal)
                        }
                    }


                        adapter = AdapterPersonal(filteredList){
                                position -> val action =ListPersonalDirections.actionListPersonalToDetallePersonal(filteredList[position])
                            findNavController().navigate(action)
                    }
                        recyclerPersonal.adapter = adapter


                }

            }

        })

        btnAlta.setOnClickListener(){
            val action = ListPersonalDirections.actionListPersonalToNewPersonal()
            findNavController().navigate(action)
        }

    }


}

