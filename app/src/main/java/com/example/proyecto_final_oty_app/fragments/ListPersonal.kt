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

    lateinit var v : View
    lateinit var recyclerPersonal : RecyclerView
    lateinit var adapter : AdapterPersonal
    lateinit var db : FirebaseFirestore
    lateinit var personales : MutableList<Personal>
    lateinit var txtTitle : TextView
    lateinit var btnAlta : Button

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

    override fun onStart() {
        super.onStart()
        db = FirebaseFirestore.getInstance()
        personales = mutableListOf()
        adapter = AdapterPersonal(personales){
                position -> val action =ListPersonalDirections.actionListPersonalToDetallePersonal(personales[position])
            findNavController().navigate(action)
        }



        recyclerPersonal.layoutManager = LinearLayoutManager(context)


        db.collection("personal")
            .get()
            .addOnSuccessListener { snapshot ->
                if (snapshot != null) {
                    for (personal in snapshot) {
                        personales.add(personal.toObject())
                    }

                    recyclerPersonal.adapter = adapter
                }
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents: ", exception)
            }

        btnAlta.setOnClickListener(){
            val action = ListPersonalDirections.actionListPersonalToNewPersonal()
            findNavController().navigate(action)
        }


    }


}
