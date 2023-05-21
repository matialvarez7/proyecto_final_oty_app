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

class DetallePersonal : Fragment() {

    lateinit var v : View
    lateinit var nombrePersonal : TextView
    lateinit var eliminarBtn : Button
    lateinit var viewModel: DetallePersonalViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        v =  inflater.inflate(R.layout.fragment_detalle_personal, container, false)
        nombrePersonal = v.findViewById(R.id.nombrePersonalDetalle)
        eliminarBtn = v.findViewById(R.id.eliminarBtn)

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetallePersonalViewModel::class.java)
        // TODO: Use the ViewModel
    }
    override fun onStart() {
        super.onStart()
        val personal = DetallePersonalArgs.fromBundle(requireArguments()).personal
        nombrePersonal.text = personal.nombre

        eliminarBtn.setOnClickListener {
            val idPersonal = personal.id // Aquí deberías obtener el id que quieres eliminar.
            CoroutineScope(Dispatchers.Main).launch {
                try{
                    viewModel.eliminarPersonal(idPersonal)
                    Toast.makeText(context, "Personal $idPersonal eliminado correctamente", Toast.LENGTH_SHORT).show()

                    findNavController().popBackStack()
                }catch(e:Exception){
                    Toast.makeText(context, "Error al eliminar el equipo: $e", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}

