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

    lateinit var prestamosFinal : MutableList<PrestamoFinal>
    lateinit var prestamos : MutableList<Prestamo>
    lateinit var prueba1 : TextView
    lateinit var prueba2 : TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_list_prestamos, container, false)
        prueba1 = v.findViewById(R.id.prueba1)
        prueba2 = v.findViewById(R.id.prueba2)
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
        prestamos = mutableListOf()
        prestamos.clear()
        adapter = AdapterPrestamo(prestamos)
        viewModel.setearRecycler()
        Log.d("mensaje","Previo a traer la coleccion")
        lifecycleScope.launch {
         prestamos = viewModel.inicializarColecciones()

            prueba1.text = prestamos[0].estadoPrestamo
            prueba2.text = prestamos[0].idPersonal
        }




        /*
        lifecycleScope.launch {
            db.collection("itemsPrestamo")
                .get()
                .addOnSuccessListener { result ->
                    Log.d("mensaje", "Entre a buscar la coleccion")
                    for (document in result) {
                        prestamos.add(document.toObject())
                    }
                    var item = prestamos[0].id
                    Log.d("mensaje", "Item:$item")
                    prueba1.text = prestamos[0].idPrestamo
                    prueba2.text = prestamos[0].estadoItem
                }
                .addOnFailureListener { exception ->
                    Log.d(ContentValues.TAG, "Error getting documents: ", exception)
                }
           } */

        //adapter = AdapterPrestamo(prestamos)
        //recyclerPrestamos.layoutManager = LinearLayoutManager(context)
        //recyclerPrestamos.adapter = adapter

        /*
        var itemsPrueba : MutableList<ItemPrestamo> = mutableListOf()
        itemsPrueba.add(ItemPrestamo("1","12345","111","Disponible"))
        itemsPrueba.add(ItemPrestamo("2","54321","222","Disponible"))
        itemsPrueba.add(ItemPrestamo("3","54","2223333","No disponible"))
        prestamosFinal = mutableListOf()
        prestamosFinal.add(PrestamoFinal("1", Date(2024, 1, 1),"En curso","pepe","papito",itemsPrueba))
        prestamosFinal.add(PrestamoFinal("2", Date(2024, 1, 1),"Finalizado","sandra","saraza",itemsPrueba))
        */

        /*
        lifecycleScope.launch {
            viewModel.inicializarColecciones()
        }

        viewModel.armarListaFinal()

        val listaPrestamoFinal = viewModel.getListaFinal()
        */

         /* { position ->
                 val action = ListPrestamosDirections.actionListPrestamosToDetallePrestamo()
                 findNavController().navigate(action)
        }*/

    }

}