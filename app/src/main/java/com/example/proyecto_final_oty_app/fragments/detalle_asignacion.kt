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
import androidx.navigation.fragment.findNavController
import com.example.proyecto_final_oty_app.R
import com.example.proyecto_final_oty_app.entities.Equipo
import com.example.proyecto_final_oty_app.entities.Personal
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetalleAsignacion : Fragment() {

    lateinit var v:View
    lateinit var personalBundle: Personal
    lateinit var equipoBundle: Equipo
    private lateinit var viewModel: DetalleAsignacionViewModel
    lateinit var nombreResponsable: TextView
    lateinit var botonEliminar: Button
    lateinit var nroInventario:TextView
    lateinit var nroAnet:TextView
    lateinit var nombreEquipo:TextView
    private lateinit var builder : AlertDialog.Builder


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v= inflater.inflate(R.layout.fragment_detalle_asignacion, container, false)
        personalBundle=DetalleAsignacionArgs.fromBundle(requireArguments()).personal //Estaba Escrito Personal con la P mayuscula.
        equipoBundle=DetalleAsignacionArgs.fromBundle(requireArguments()).equipo // Estaba escrito Equipo con la E mayuscula
        nroAnet=v.findViewById(R.id.nroAnet)
        nroInventario=v.findViewById(R.id.nroInventario)
        nombreEquipo=v.findViewById(R.id.nombreEquipo)
        nombreResponsable=v.findViewById(R.id.nombreResponsable)
        botonEliminar=v.findViewById(R.id.borrar)
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetalleAsignacionViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onStart() {
        super.onStart()
        nombreResponsable.text = personalBundle.nombre
        nroAnet.text= equipoBundle.anet
        nroInventario.text= equipoBundle.inventario
        nombreEquipo.text= equipoBundle.nombre

        builder = AlertDialog.Builder(context)

        botonEliminar.setOnClickListener {

            val elementoId = DetalleAsignacionArgs.fromBundle(requireArguments()).idAsignacion
            builder.setMessage("Estarás eliminando la asignacion de "+nombreResponsable.text+" ¿Deseas continuar?")
                .setCancelable(true)
                .setNegativeButton("no"){ dialogInterface, it ->
                    dialogInterface.cancel()
                }
                .setPositiveButton("Si"){dialogInterface,it->
                    CoroutineScope(Dispatchers.Main).launch {
                        try {
                            viewModel.eliminarAsignacion(elementoId)
                            Toast.makeText(
                                context,
                                "Asignacion eliminada correctamente",
                                Toast.LENGTH_SHORT
                            ).show()
                            findNavController().navigateUp()
                        } catch (e: Exception) {
                            Toast.makeText(context, "Error al eliminar la asignacion: $e", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }

                }

                .show()

        }

    }
}