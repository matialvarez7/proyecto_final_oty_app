package com.example.proyecto_final_oty_app.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_final_oty_app.R
import com.example.proyecto_final_oty_app.adapters.AdapterCrearPrestamo
import com.example.proyecto_final_oty_app.adapters.AdapterPrestamo
import com.example.proyecto_final_oty_app.entities.Personal
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class NewPrestamo : Fragment() {

    companion object {
        fun newInstance() = NewPrestamo()
    }
    lateinit var v : View
    lateinit var dniResponsable : EditText
    lateinit var buscarResponsable : Button
    lateinit var aniadirEquipo : Button
    lateinit var nombreResponsable : TextView
    lateinit var apellidoResponsable : TextView
    lateinit var recyclerEquiposPrestamos : RecyclerView
    lateinit var equiposPrestamoAdapter : AdapterCrearPrestamo
    lateinit var confirmarPrestamo : Button
    lateinit var cancelarPrestamo : Button
    var personal : Personal? = null
    //private lateinit var viewModel: NewPrestamoViewModel
    //private val sharedViewModel : NewPrestamoViewModel by activityViewModels()
    private val viewModel: NewPrestamoViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        v = inflater.inflate(R.layout.fragment_new_prestamo, container, false)
        dniResponsable = v.findViewById(R.id.dniResponsable)
        buscarResponsable = v.findViewById(R.id.buscarResponsable)
        nombreResponsable = v.findViewById(R.id.nombreResponsable)
        apellidoResponsable = v.findViewById(R.id.apellidoResponsable)
        aniadirEquipo = v.findViewById(R.id.aniadirEquipo)
        recyclerEquiposPrestamos = v.findViewById(R.id.recyclerEquiposPrestamo)
        confirmarPrestamo = v.findViewById(R.id.confirmarPrestamo)
        cancelarPrestamo = v.findViewById(R.id.cancelarPrestamo)
        return v
    }

    /*override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NewPrestamoViewModel::class.java)
        // TODO: Use the ViewModel
    }*/

    override fun onStart() {
        super.onStart()
        // Crea el ID del préstamo
        viewModel.setIdPrestamo()

        /* Cada vez que ocurra ocurra una eliminación de un equipo que está en la lista
        de equipos asignado se actualiza lo que muestra en el recyclerview*/
        viewModel.liveListaEquipos.observe(viewLifecycleOwner, Observer{ lista ->
            equiposPrestamoAdapter = AdapterCrearPrestamo(lista){position ->
                viewModel.eliminarEquipoAniadido(position)
            }
            recyclerEquiposPrestamos.layoutManager = LinearLayoutManager(context)
            recyclerEquiposPrestamos.adapter = equiposPrestamoAdapter
        })

        /* Busca el responsable a asignar por número de DNI. Si el DNI no es válido o no existe informa
        la situación.
         */
        buscarResponsable.setOnClickListener(){
            if(viewModel.campoDniVacío(dniResponsable.text.toString())){
                Snackbar.make(v, "El DNI no puede estar vacío", Snackbar.LENGTH_LONG).show()
            }else{
                if(viewModel.dniInvalido(dniResponsable.text.toString())){
                    Snackbar.make(v, "No es un DNI valido", Snackbar.LENGTH_LONG).show()
                }else{
                    lifecycleScope.launch {
                        personal = viewModel.buscarResponsable(dniResponsable.text.toString())
                        if(personal == null){
                            Snackbar.make(v, "No existe el personal", Snackbar.LENGTH_LONG).show()
                        }
                        else{
                            nombreResponsable.text = viewModel.mostrarNombrePersonal()
                            apellidoResponsable.text = viewModel.mostrarApellidoPersonal()
                        }
                    }
                }
            }

        }

        // Navega hacia el fragment para añadir equipos al préstamo
        aniadirEquipo.setOnClickListener(){
            val action = NewPrestamoDirections.actionNewPrestamo2ToAniadirEquipoPrestamo(viewModel.idPrestamo)
            findNavController().navigate(action)
        }

        // Confirmación de un préstamo. Antes de confirmar el mismo solicita una confirmación
        confirmarPrestamo.setOnClickListener() {
            AlertDialog.Builder(context).setTitle("Confirmar préstamo").setMessage("¿Desea confirmar el nuevo préstamo?")
                .setPositiveButton(android.R.string.ok) { _, _ ->
                    lifecycleScope.launch {
                        if (viewModel.confirmarPrestamo()) {
                            Snackbar.make(v, "Prestamo confirmado", Snackbar.LENGTH_LONG)
                                .show()
                            findNavController().popBackStack()
                        } else {
                            Snackbar.make(
                                v,
                                "Error al crear pŕestamo",
                                Snackbar.LENGTH_LONG
                            ).show()
                        }
                    }
                }
                .setNegativeButton(android.R.string.cancel, null)
                .show()
        }

        // Cancela la creación del préstamo y redirige a la pantalla anterior
        cancelarPrestamo.setOnClickListener(){
            lifecycleScope.launch {
                if(viewModel.cancelarPrestamo()){
                    Snackbar.make(v, "Prestamo cancelado", Snackbar.LENGTH_LONG).show()
                    findNavController().popBackStack()
                }
            }
        }

    }

}