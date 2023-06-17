package com.example.proyecto_final_oty_app.fragments

import android.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
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
    private lateinit var builder : AlertDialog.Builder


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
        builder = AlertDialog.Builder(context)

        responsablePrestamo.text = "${prestamo.nombre} ${prestamo.apellido}"
        fechaPrestamo.text = prestamo.fechaPrestamo.toString()
        lifecycleScope.launch {
            viewModel.obtenerEquipos(prestamo.itemsPrestamo)
            var equipos = viewModel.getListadoEquiposPrestamo()
            adapter = AdapterDetallePrestamo(equipos){ position ->
                val action = DetallePrestamoDirections.actionDetallePrestamoToDetallePrestamoEquipo(equipos[position])
                findNavController().navigate(action)
            }
            recyclerItems.layoutManager = LinearLayoutManager(context)
            recyclerItems.adapter = adapter
        }

        finalizarPrestamoBtn.setOnClickListener {
            val prestamo = DetallePrestamoArgs.fromBundle(requireArguments()).prestamoFinal

            builder.setMessage("Estarás finalizando el prestamo ¿Estás seguro?")
                .setCancelable(true)
                .setNegativeButton("no"){ dialogInterface, it ->
                    dialogInterface.cancel()
                }
                .setPositiveButton("Si"){dialogInterface,it->
                    CoroutineScope(Dispatchers.Main).launch {
                        try {
                            viewModel.finalizarPrestamo(prestamo)
                            Toast.makeText(context, "Préstamo finalizado correctamente", Toast.LENGTH_SHORT).show()
                            findNavController().popBackStack()
                        } catch(e:Exception) {
                            Toast.makeText(context, "Error al finalizar el préstamo: $e", Toast.LENGTH_SHORT).show()
                        }
                    }

                }

                .show()

        }

    }

}