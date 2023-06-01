package com.example.proyecto_final_oty_app.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.proyecto_final_oty_app.R

class DetallePersonal : Fragment() {



    companion object {
        fun newInstance() = DetallePersonal()
    }
    lateinit var v : View
    lateinit var viewModel : DetallePersonalViewModel
    lateinit var baseDNI : TextView
    lateinit var baseNombre : TextView
    lateinit var baseApellido : TextView
    lateinit var baseArea : TextView
    lateinit var editarBtn: Button
    lateinit var eliminarBtn: Button

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

/*
    override fun onStart() {
        super.onStart()
        abm = PersonalABM() // Inicializar la instancia de PersonalABM

        val personal = DetallePersonalArgs.fromBundle(requireArguments()).personal

=======
    override fun onStart() {
        super.onStart()
        val personal = DetallePersonalArgs.fromBundle(requireArguments()).personal
        baseDNI.text = personal.dni
        baseNombre.text = personal.nombre
        baseApellido.text = personal.apellido
        baseArea.text = personal.area

        editarBtn.setOnClickListener(){
            val action = DetallePersonalDirections.actionDetallePersonalToEditarDetallePersonal(personal)
            findNavController().navigate(action)
        }
>>>>>>> origin/us-48-altaAsignacion
        eliminarBtn.setOnClickListener {
            val idPersonal = personal.id // Aquí deberías obtener el id que quieres eliminar.
            CoroutineScope(Dispatchers.Main).launch {
                try{
                    viewModel.eliminarPersonal(idPersonal)
                    Toast.makeText(context, "Personal $idPersonal eliminado correctamente", Toast.LENGTH_SHORT).show()

<<<<<<< HEAD
=======
                    findNavController().popBackStack()
>>>>>>> origin/us-48-altaAsignacion
                }catch(e:Exception){
                    Toast.makeText(context, "Error al eliminar el equipo: $e", Toast.LENGTH_SHORT).show()
                }
            }
        }
<<<<<<< HEAD
    }
*/}


