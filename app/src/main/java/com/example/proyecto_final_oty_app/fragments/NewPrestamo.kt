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
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_final_oty_app.R
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
    var personal : Personal? = null
    private lateinit var viewModel: NewPrestamoViewModel

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
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NewPrestamoViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onStart() {
        super.onStart()
        viewModel.setIdPrestamo()
        recyclerEquiposPrestamos.layoutManager = LinearLayoutManager(context)
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
                            nombreResponsable.text = personal!!.nombre
                            apellidoResponsable.text = personal!!.apellido
                        }
                    }
                }
            }

        }

        aniadirEquipo.setOnClickListener(){

        }

    }

}