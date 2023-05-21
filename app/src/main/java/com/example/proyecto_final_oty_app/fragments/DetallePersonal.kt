package com.example.proyecto_final_oty_app.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.proyecto_final_oty_app.R
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.proyecto_final_oty_app.entities.PersonalABM
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetallePersonal : Fragment() {

    lateinit var v : View
    lateinit var eliminarBtn:Button
    lateinit var abm: PersonalABM
    lateinit var viewModel : DetallePersonalViewModel


    companion object {
        fun newInstance() = DetallePersonal()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_detalle_personal, container, false)
        eliminarBtn=v.findViewById(R.id.EliminarBtn)
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetallePersonalViewModel::class.java)
        // TODO: Use the ViewModel
    }


    override fun onStart() {
        super.onStart()
        abm = PersonalABM() // Inicializar la instancia de PersonalABM

        val personal = DetallePersonalArgs.fromBundle(requireArguments()).personal

        eliminarBtn.setOnClickListener {
            val idPersonal = personal.id // Aquí deberías obtener el id que quieres eliminar.
            CoroutineScope(Dispatchers.Main).launch {
                try{
                    viewModel.eliminarPersonal(idPersonal)
                    Toast.makeText(context, "Personal $idPersonal eliminado correctamente", Toast.LENGTH_SHORT).show()

                }catch(e:Exception){
                    Toast.makeText(context, "Error al eliminar el equipo: $e", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}