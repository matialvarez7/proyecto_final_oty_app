package com.example.proyecto_final_oty_app.fragments

import android.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import com.example.proyecto_final_oty_app.R
import com.example.proyecto_final_oty_app.entities.Equipo
import com.google.android.material.snackbar.Snackbar


class EditDetalleEquipo : Fragment() {

    lateinit var bot: Button
    lateinit var vista:View
    lateinit var nombre: EditText
    lateinit var inventario: EditText
    lateinit var anet: EditText
    lateinit var estado:EditText
    private lateinit var builder : AlertDialog.Builder
    private lateinit var viewModel: EditDetalleEquipoViewModel
    companion object {
        fun newInstance() = EditDetalleEquipo()
    }




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



    override fun onStart() {
        super.onStart()
        val equipo = DetalleEquipoArgs.fromBundle(requireArguments()).equipo
        builder = AlertDialog.Builder(context)
        inventario.setText(equipo.inventario.toString())
        anet.setText(equipo.anet.toString())
        nombre.setText(equipo.nombre.toString())
        estado.setText(equipo.estado)


        if(equipo!=null) {
            bot.setOnClickListener {
                builder.setMessage("¿Desea Confirmar?")
                    .setCancelable(true)
                    .setNegativeButton("no"){ dialogInterface, it ->
                        dialogInterface.cancel()
                    }
                    .setPositiveButton("Si"){dialogInterface,it->
                        var eq = Equipo(equipo.id, inventario.text.toString(), nombre.text.toString(), anet.text.toString(), estado.text.toString())
                        viewModel.actualizarEquipo(eq)
                        Snackbar.make(vista,"Se edito correctamente",1000).show()
                        findNavController().navigateUp()
                    }

                    .show()


            }


            }
        }
}


