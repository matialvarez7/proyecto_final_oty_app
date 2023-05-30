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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_final_oty_app.R
import com.example.proyecto_final_oty_app.adapters.AdapterAsignacion
import com.example.proyecto_final_oty_app.adapters.AdapterEquipo
import com.example.proyecto_final_oty_app.entities.AsignacionDocente
import com.example.proyecto_final_oty_app.entities.Equipo
import com.example.proyecto_final_oty_app.entities.Personal
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

class ListAsignacionesDocentes : Fragment() {


    lateinit var v : View
    lateinit var recyclerAsignacionDocente : RecyclerView
    lateinit var adapter : AdapterAsignacion
    lateinit var db : FirebaseFirestore
    lateinit var equipos : MutableList<Equipo>
    lateinit var asignaciones : MutableList<AsignacionDocente>
    lateinit var personales : MutableList<Personal>


    companion object {
        fun newInstance() = ListAsignacionesDocentes()
    }

    private lateinit var viewModel: ListAsignacionesDocentesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v= inflater.inflate(R.layout.fragment_list_asignaciones_docentes, container, false)
        recyclerAsignacionDocente = v.findViewById(R.id.listaAsignaciones)

    return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ListAsignacionesDocentesViewModel::class.java)
        // TODO: Use the ViewModel
    }
    private fun registro(){
        db.collection("asignaciones")
            .get()
            .addOnSuccessListener { snapshot ->
                if (snapshot != null) {
                    for (equipo in snapshot) {
                        asignaciones.add(equipo.toObject())
                    }

                }
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents: ", exception)
            }
        db.collection("personal")
            .get()
            .addOnSuccessListener { snapshot ->
                if (snapshot != null) {
                    for (equipo in snapshot) {
                        personales.add(equipo.toObject())
                    }

                }
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents: ", exception)
            }
        db.collection("equipos")
            .get()
            .addOnSuccessListener { snapshot ->
                if (snapshot != null) {
                    for (equipo in snapshot) {
                        equipos.add(equipo.toObject())
                    }
                    recyclerAsignacionDocente.adapter = adapter //Va aca porque equipos esta vacio, entonces aca le paso el adaptador con los equipos agregados.
                }
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents: ", exception)
            }
    }

    override fun onStart() {
        super.onStart()
        db = FirebaseFirestore.getInstance()
        equipos = mutableListOf()
        personales = mutableListOf()
        asignaciones= mutableListOf()



        adapter = AdapterAsignacion(equipos, personales,asignaciones){
                position ->
            val posE=buscarEquipoPos(asignaciones[position].idEquipo)
            val posP=buscarPersonalPos(asignaciones[position].idPersonal)
            val action = ListAsignacionesDocentesDirections.actionListAsignacionesDocentesToDetalleAsignacion(asignaciones[position].id,equipos[posE],personales[posP])
            findNavController().navigate(action)
        }
        registro()
        recyclerAsignacionDocente.layoutManager = LinearLayoutManager(context)







    }

    fun buscarEquipoPos( equipoId: String): Int {
        var indice = 0
        var indiceUltimo = -1
        while (indice < equipos.size) {
            if (equipos[indice].id == equipoId) {
                return indice
            }
            indiceUltimo = indice
            indice++
        }
        return indiceUltimo
    }

    fun buscarPersonalPos( personaId: String): Int {
        var indice = 0
        var indiceUltimo = -1
        while (indice < personales.size) {
            if (personales[indice].id == personaId) {
                return indice
            }
            indiceUltimo = indice
            indice++
        }
        return indiceUltimo
    }

}