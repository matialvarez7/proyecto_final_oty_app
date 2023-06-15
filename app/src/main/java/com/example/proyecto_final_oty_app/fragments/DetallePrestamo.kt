package com.example.proyecto_final_oty_app.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_final_oty_app.R
import com.example.proyecto_final_oty_app.adapters.AdapterDetallePrestamo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetallePrestamo : Fragment() {

    companion object {
        fun newInstance() = DetallePrestamo()
    }

    private lateinit var viewModel: DetallePrestamoViewModel
    lateinit var v : View
    lateinit var recyclerItems : RecyclerView
    lateinit var adapter : AdapterDetallePrestamo
    lateinit var responsablePrestamo : TextView
    lateinit var fechaPrestamo : TextView
    lateinit var finalizarPrestamoBtn: Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_detalle_prestamo, container, false)
        recyclerItems = v.findViewById(R.id.recyclerItems)
        responsablePrestamo = v.findViewById(R.id.responsableDetallePrestamo)
        fechaPrestamo = v.findViewById(R.id.fechaDetallePrestamo)
        finalizarPrestamoBtn = v.findViewById(R.id.finalizarPrestamoBtn)
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetallePrestamoViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onStart() {
        super.onStart()
        val prestamo = DetallePrestamoArgs.fromBundle(requireArguments()).prestamoFinal
        //En la vista muestro los detalles del responsable y fecha de prestamo
        responsablePrestamo.text = "${prestamo.nombre} ${prestamo.apellido}"
        fechaPrestamo.text = prestamo.fechaPrestamo.toString()

        //Necesito armar una lista live, con ItemPrestamoInterno, con los datos para DetallePrestamo y DetalleEquipoPrestamo
        lifecycleScope.launch {
            viewModel.obtenerEquiposDeDB()
            //Necesito poder observar los cambios en dicha lista, principalmente para cuando cambian los estados de sus items.
            var listaItemsPrestamo = viewModel.listaItemsFinal
            listaItemsPrestamo.observe(viewLifecycleOwner, Observer { lista ->

            })

            //Luego la seteo en el adapter y la renderizo
            adapter = AdapterDetallePrestamo(listaItemsPrestamo) { position ->
                val action = DetallePrestamoDirections.actionDetallePrestamoToDetallePrestamoEquipo()
                findNavController().navigate(action)
            }
            recyclerItems.layoutManager = LinearLayoutManager(context)
            recyclerItems.adapter = adapter
        }
            /*
        lifecycleScope.launch {
            viewModel.obtenerEquipos(prestamo.itemsPrestamo)
            var equipos = viewModel.getListadoEquiposPrestamo()
            adapter = AdapterDetallePrestamo(equipos){ position ->
                val action = DetallePrestamoDirections.actionDetallePrestamoToDetallePrestamoEquipo(equipos[position])
                findNavController().navigate(action)
            }
            recyclerItems.layoutManager = LinearLayoutManager(context)
            recyclerItems.adapter = adapter
        } */

            finalizarPrestamoBtn.setOnClickListener {
                val prestamo = DetallePrestamoArgs.fromBundle(requireArguments()).prestamoFinal
                CoroutineScope(Dispatchers.Main).launch {
                    try {
                        viewModel.finalizarPrestamo(prestamo)
                        Toast.makeText(
                            context,
                            "Préstamo finalizado correctamente",
                            Toast.LENGTH_SHORT
                        ).show()
                        findNavController().popBackStack()
                    } catch (e: Exception) {
                        Toast.makeText(
                            context,
                            "Error al finalizar el préstamo: $e",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
    }
}