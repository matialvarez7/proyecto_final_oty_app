package com.example.proyecto_final_oty_app.fragments

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_final_oty_app.R
import com.example.proyecto_final_oty_app.adapters.AdapterPersonal
import com.example.proyecto_final_oty_app.entities.Personal
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_list_personal, container, false)
        recyclerPersonal = v.findViewById(R.id.ListaPersonal)
        txtTitle = v.findViewById(R.id.titleListaPersonal)
        btnAlta = v.findViewById(R.id.bottonAregarPersonal)

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

        adapter = AdapterPersonal(personales){
                position -> val action =ListPersonalDirections.actionListPersonalToDetallePersonal(personales[position])
            findNavController().navigate(action)
        }

        recyclerPersonal.layoutManager = LinearLayoutManager(context)

        viewModel.mostrarColeccion(personales,recyclerPersonal,adapter)

        btnAlta.setOnClickListener(){
            val action = ListPersonalDirections.actionListPersonalToNewPersonal()
            findNavController().navigate(action)
        }

    }

}
