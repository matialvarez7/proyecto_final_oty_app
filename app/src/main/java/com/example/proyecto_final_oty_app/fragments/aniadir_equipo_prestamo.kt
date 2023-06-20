package com.example.proyecto_final_oty_app.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.proyecto_final_oty_app.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class AniadirEquipoPrestamo : Fragment() {

    companion object {
        fun newInstance() = AniadirEquipoPrestamo()
    }
    private lateinit var viewModel: AniadirEquipoPrestamoViewModel
    private val sharedViewModel : NewPrestamoViewModel by activityViewModels()
    private val sharedScaner : LectorViewModel by activityViewModels()
    //private val viewModel: NewPrestamoViewModel by activityViewModels()
    lateinit var v : View
    lateinit var inventario : EditText
    lateinit var nombreEquipo : TextView
    lateinit var nroAnetEquipo : TextView
    lateinit var estadoEquipo : TextView
    lateinit var buscar : Button
    lateinit var aniadir : Button
    lateinit var scanInventario : ImageButton


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_aniadir_equipo_prestamo, container, false)
        inventario = v.findViewById(R.id.invEquipoPrestamo)
        nombreEquipo = v.findViewById(R.id.nomEquipoPrestamo)
        nroAnetEquipo = v.findViewById(R.id.anetEquipoPrestamo)
        estadoEquipo = v.findViewById(R.id.estadoEquipoPrestamo)
        buscar =  v.findViewById(R.id.buscarEquipo)
        aniadir = v.findViewById(R.id.confirmarEquipoPrestamo)
        scanInventario = v.findViewById(R.id.scanInventarioPrestamo)
        return v
    }

    /*override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //viewModel = ViewModelProvider(this).get(AniadirEquipoPrestamoViewModel::class.java)
        // TODO: Use the ViewModel
    }*/

    override fun onStart() {
        super.onStart()
        val id = AniadirEquipoPrestamoArgs.fromBundle(requireArguments()).idPrestamo

        scanInventario.setOnClickListener{
            val action = AniadirEquipoPrestamoDirections.actionAniadirEquipoPrestamoToLector("0".toInt())
            findNavController().navigate(action)
        }
        sharedScaner.valorEscaneadoInventario.observe(viewLifecycleOwner, Observer { valor ->
            inventario.setText(valor)
        })

        buscar.setOnClickListener(){
            if(sharedViewModel.campoInventarioVacío(inventario.text.toString())) {
                Snackbar.make(v, "El número de inventario no puede estar vacío", Snackbar.LENGTH_LONG).show()
            }
            else{
                lifecycleScope.launch {
                    if(!sharedViewModel.buscarEquipo(inventario.text.toString())){
                        Snackbar.make(v, "El número de inventario no existe", Snackbar.LENGTH_LONG).show()
                    }
                    else{
                        if(sharedViewModel.noEsEquipoPrestamo()){
                            Snackbar.make(v, "El equipo no es apto préstamo", Snackbar.LENGTH_LONG).show()
                        }else{
                            mostrarDatos()
                        }

                    }
                }
            }
        }
        aniadir.setOnClickListener(){
            lifecycleScope.launch {
                if(!sharedViewModel.equipoYaAgregado(inventario.text.toString())){
                    if(sharedViewModel.confirmarEquipo()){
                        limpiarDatos()
                        Snackbar.make(v, "Equipo añadido al préstamo", Snackbar.LENGTH_LONG).show()

                    }else{
                        Snackbar.make(v, "Error al añadir un equipo", Snackbar.LENGTH_LONG).show()
                    }
                }else{
                    Snackbar.make(v, "El equipo ya fue agregado", Snackbar.LENGTH_LONG).show()
                }
            }
        }

    }

    fun mostrarDatos() {
        if(sharedViewModel.equipo != null){
            nombreEquipo.text = sharedViewModel.equipo!!.nombre
            nroAnetEquipo.text = sharedViewModel.equipo!!.anet
            estadoEquipo.text = sharedViewModel.equipo!!.estado
        }

    }

    fun limpiarDatos() {
        inventario.text.clear()
        nombreEquipo.text = ""
        nroAnetEquipo.text = ""
        estadoEquipo.text = ""
    }

}