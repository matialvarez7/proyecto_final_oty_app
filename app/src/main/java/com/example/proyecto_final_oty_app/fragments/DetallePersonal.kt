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
import android.widget.EditText
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.proyecto_final_oty_app.entities.Personal
import com.example.proyecto_final_oty_app.entities.PersonalABM
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetallePersonal : Fragment() {


    lateinit var v : View
    lateinit var baseDNI : TextView
    lateinit var baseNombre : TextView
    lateinit var baseApellido : TextView
    lateinit var baseArea : TextView
    lateinit var editarBtn: Button
    lateinit var eliminarBtn: Button
    lateinit var viewModel: DetallePersonalViewModel
    private lateinit var abm: PersonalABM
    private lateinit var builder : AlertDialog.Builder

    companion object {
        fun newInstance() = DetallePersonal()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        v =  inflater.inflate(R.layout.fragment_detalle_personal, container, false)
        baseDNI = v.findViewById(R.id.baseDNI)
        baseNombre = v.findViewById(R.id.baseNombre)
        baseApellido = v.findViewById(R.id.baseApellido)
        baseArea = v.findViewById(R.id.baseArea)
        editarBtn=v.findViewById(R.id.editarBtn)
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
        baseDNI.text = personal.dni
        baseNombre.text = personal.nombre
        baseApellido.text = personal.apellido
        baseArea.text = personal.area
        builder = AlertDialog.Builder(context)

        editarBtn.setOnClickListener(){
            val action = DetallePersonalDirections.actionDetallePersonalToEditarDetallePersonal(personal)
            findNavController().navigate(action)
        }
        eliminarBtn.setOnClickListener {

            val idPersonal = personal.id // Aquí deberías obtener el id que quieres eliminar.
            builder.setMessage("Estarás eliminando el registro de "+baseNombre.text+" "+baseApellido.text+" ¿Deseas continuar?")
                .setCancelable(true)
                .setNegativeButton("no"){ dialogInterface, it ->
                    dialogInterface.cancel()
                }
                .setPositiveButton("Si"){dialogInterface,it->
                    CoroutineScope(Dispatchers.Main).launch {
                        try{
                            viewModel.eliminarPersonal(idPersonal)
                            Toast.makeText(context, "Personal $idPersonal eliminado correctamente", Toast.LENGTH_SHORT).show()

                            findNavController().popBackStack()
                        }catch(e:Exception){
                            Toast.makeText(context, "Error al eliminar el personal: $e", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                .show()

        }

    }

}


