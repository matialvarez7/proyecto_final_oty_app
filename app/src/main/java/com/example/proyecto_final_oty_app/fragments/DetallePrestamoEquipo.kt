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
import androidx.navigation.fragment.findNavController
import com.example.proyecto_final_oty_app.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetallePrestamoEquipo : Fragment() {

    companion object {
        fun newInstance() = DetallePrestamoEquipo()
    }

    private lateinit var viewModel: DetallePrestamoEquipoViewModel
    lateinit var v : View
    lateinit var inventario : TextView
    lateinit var nombre : TextView
    lateinit var anet : TextView
    lateinit var estado : TextView
    lateinit var eliminarBtn: Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_detalle_prestamo_equipo, container, false)
        inventario = v.findViewById(R.id.inventarioDetPrestEquipo)
        nombre = v.findViewById(R.id.nombreDetPrestEquipo)
        anet = v.findViewById(R.id.anetDetPrestEquipo)
        estado = v.findViewById(R.id.estadoDetPrestEquipo)
        eliminarBtn = v.findViewById(R.id.EliminarBtn)
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetallePrestamoEquipoViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onStart() {
        super.onStart()
        val equipo = DetallePrestamoEquipoArgs.fromBundle(requireArguments()).equipo

        inventario.text = equipo.inventario
        nombre.text = equipo.nombre
        anet.text = equipo.anet
        estado.text = equipo.estado

        eliminarBtn.setOnClickListener {
            val idEquipo = equipo.id // Aquí deberías obtener el id del Equipo que quieres marcar como disponible.
            CoroutineScope(Dispatchers.Main).launch {
                try {
                    val itemPrestamo = viewModel.obtenerItemPrestamoPorIdEquipo(idEquipo)
                    if (itemPrestamo != null) {
                        viewModel.devolverEquipoYMarcarItemPrestamo(itemPrestamo.id, idEquipo)
                        Toast.makeText(context, "Equipo devuelto y item de préstamo marcado correctamente", Toast.LENGTH_SHORT).show()
                        findNavController().popBackStack()
                    } else {
                        Toast.makeText(context, "No se encontró el item de préstamo correspondiente", Toast.LENGTH_SHORT).show()
                    }
                } catch(e:Exception) {
                    Toast.makeText(context, "Error al devolver el equipo y marcar el item de préstamo: $e", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}