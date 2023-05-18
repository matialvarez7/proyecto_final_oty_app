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
import androidx.lifecycle.ViewModel
import com.example.proyecto_final_oty_app.R
import com.example.proyecto_final_oty_app.entities.Equipo
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.runBlocking

class EditDetalleEquipo : Fragment() {

    companion object {
        fun newInstance() = EditDetalleEquipo()
    }

    private lateinit var viewModel: EditDetalleEquipoViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit_detalle_equipo, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(EditDetalleEquipoViewModel::class.java)
        // TODO: Use the ViewModel
    }

    lateinit var bot: Button
    lateinit var vista:View
    lateinit var anet: EditText
    lateinit var inv: TextView
    lateinit var inventario: EditText
    lateinit var nombre: EditText

    override fun onStart() {
        super.onStart()
        val equipo: Equipo?
        runBlocking {
            equipo= viewModel.obtenerEquipoAleatorio()
        }
        if(equipo!=null) {
            inv.setText("")
            bot.setOnClickListener {
                var eq = Equipo(equipo.id, inventario.text.toString(), nombre.text.toString(), anet.text.toString(), equipo.estado)
                viewModel.actualizarEquipo(eq)
                runBlocking { if(viewModel.existeEquipo(eq)){
                    Snackbar.make(vista,"exite",1000).show()
                }
                }
            }
        }
    }

}