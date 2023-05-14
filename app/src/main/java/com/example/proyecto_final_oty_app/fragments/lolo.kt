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
import com.example.proyecto_final_oty_app.R
import com.example.proyecto_final_oty_app.entities.Equipo
import com.google.android.material.behavior.HideBottomViewOnScrollBehavior
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.runBlocking

class lolo : Fragment() {

    companion object {
        fun newInstance() = lolo()
    }

    private lateinit var viewModel: LoloViewModel
    lateinit var bot:Button
    lateinit var vista:View
lateinit var anet:EditText
    lateinit var inv: TextView
    lateinit var inventario:EditText
    lateinit var nombre:EditText
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vista= inflater.inflate(R.layout.fragment_lolo, container, false)
        inv=vista.findViewById(R.id.text)
        bot=vista.findViewById(R.id.editar1)
        inventario=vista.findViewById(R.id.nro_inv)
        anet=vista.findViewById(R.id.nro_inv2)
        nombre=vista.findViewById(R.id.nro_inv3)
    return vista
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoloViewModel::class.java)
        // TODO: Use the ViewModel
    }

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