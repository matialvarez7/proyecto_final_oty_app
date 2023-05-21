package com.example.proyecto_final_oty_app.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.proyecto_final_oty_app.R
import com.example.proyecto_final_oty_app.entities.Equipo
import com.google.android.material.snackbar.Snackbar

class EditDetalleEquipo : Fragment() {

    companion object {
        fun newInstance() = EditDetalleEquipo()
    }

    private lateinit var viewModel: EditDetalleEquipoViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vista= inflater.inflate(R.layout.fragment_edit_detalle_equipo, container, false)
        bot=vista.findViewById(R.id.editar1)
        anet=vista.findViewById(R.id.nro_inv2)
        inventario=vista.findViewById(R.id.nro_inv)
        nombre=vista.findViewById(R.id.nro_inv3)
        estado=vista.findViewById(R.id.estado)
        return vista
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(EditDetalleEquipoViewModel::class.java)
        // TODO: Use the ViewModel
    }

    lateinit var bot: Button
    lateinit var vista:View
    lateinit var nombre: EditText

    lateinit var inventario: EditText
    lateinit var anet: EditText

    lateinit var estado:EditText

    override fun onStart() {
        super.onStart()
         var equipo = DetalleEquipoArgs.fromBundle(requireArguments()).equipo
        inventario.setText(equipo.inventario.toString())
        anet.setText(equipo.anet.toString())
        nombre.setText(equipo.nombre.toString())
        estado.setText(equipo.estado)
        if(equipo!=null) {

            bot.setOnClickListener {
                var eq = Equipo(equipo.id, inventario.text.toString(), nombre.text.toString(), anet.text.toString(), estado.text.toString())
                viewModel.actualizarEquipo(eq)
                Snackbar.make(vista,"Se edito correctamente",1000).show()
                findNavController().popBackStack()
            }
            }
        }
    }

