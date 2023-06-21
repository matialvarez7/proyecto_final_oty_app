package com.example.proyecto_final_oty_app.fragments
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_final_oty_app.R
import com.example.proyecto_final_oty_app.adapters.AdapterAsignacion
import com.example.proyecto_final_oty_app.entities.AsignacionDocente
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.Locale

class ListAsignacionesDocentes : Fragment() {


    lateinit var v : View
    lateinit var recyclerAsignacionDocente : RecyclerView
    lateinit var adapter : AdapterAsignacion
    lateinit var btnAltaAsignacion : Button
    lateinit var buscadorAsignaciones : SearchView



    companion object {
        fun newInstance() = ListAsignacionesDocentes()
    }

    private lateinit var viewModel: ListAsignacionesDocentesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v= inflater.inflate(R.layout.fragment_list_asignaciones_docentes, container, false)
        btnAltaAsignacion = v.findViewById(R.id.bottonAgregarAsignacion)
        recyclerAsignacionDocente = v.findViewById(R.id.listaAsignaciones)
        buscadorAsignaciones = v.findViewById(R.id.searchViewAsignaciones)

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ListAsignacionesDocentesViewModel::class.java)
        // TODO: Use the ViewModel
    }


    override fun onStart() {
        super.onStart()
        runBlocking {
            viewModel.registro()
        }
        var equipos=viewModel.equipos
        var personales=viewModel.personales
        var asignaciones=viewModel.asignaciones

        recyclerAsignacionDocente.setHasFixedSize(true)
        recyclerAsignacionDocente.layoutManager = LinearLayoutManager(context)
        Log.e("personales"," "+personales.size)
        Log.e("equipos"," "+equipos.size)
        Log.e("asignaciones"," "+asignaciones.size)


        adapter = AdapterAsignacion(equipos, personales,asignaciones){
                position ->
            val posE=viewModel.buscarEquipoPos(asignaciones[position].idEquipo)
            val posP=viewModel.buscarPersonalPos(asignaciones[position].idPersonal)
            val action = ListAsignacionesDocentesDirections.actionListAsignacionesDocentesToDetalleAsignacion(asignaciones[position].id,equipos[posE],personales[posP])
            findNavController().navigate(action)
        }
        recyclerAsignacionDocente.adapter = adapter

        buscadorAsignaciones.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }

            fun filterList(query : String?){
                if(query != null){
                    val filteredList = mutableListOf<AsignacionDocente>()
                    for (asignacion in asignaciones){
                        val pos = viewModel.buscarPersonalPos(asignacion.idPersonal)
                        if(personales[pos].nombre.lowercase().contains(query) || personales[pos].nombre.contains(query)
                            ||personales[pos].apellido.lowercase().contains(query) || personales[pos].apellido.contains(query)  ){
                            filteredList.add(asignacion)
                        }
                    }


                    adapter = AdapterAsignacion(equipos, personales,filteredList){
                            position ->
                        val posE=viewModel.buscarEquipoPos(filteredList[position].idEquipo)
                        val posP=viewModel.buscarPersonalPos(filteredList[position].idPersonal)
                        val action = ListAsignacionesDocentesDirections.actionListAsignacionesDocentesToDetalleAsignacion(filteredList[position].id,equipos[posE],personales[posP])
                        findNavController().navigate(action)
                    }
                    recyclerAsignacionDocente.adapter = adapter


                }

            }

        })


        btnAltaAsignacion.setOnClickListener(){
            val action = ListAsignacionesDocentesDirections.actionListAsignacionesDocentesToNewAsignacion()
            findNavController().navigate(action)
        }


    }



}