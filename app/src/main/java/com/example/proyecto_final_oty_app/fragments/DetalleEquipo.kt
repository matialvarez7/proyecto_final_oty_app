package com.example.proyecto_final_oty_app.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.proyecto_final_oty_app.R
import com.example.proyecto_final_oty_app.entities.EquipoABM
import com.example.proyecto_final_oty_app.entities.PersonalABM
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetalleEquipo : Fragment() {
    private lateinit var v:View
    private lateinit var eliminarBtn: Button
    private lateinit var abm: EquipoABM
    private val viewModel: DetalleEquipoViewModel by viewModels()
    companion object {
        fun newInstance() = DetalleEquipo()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v= inflater.inflate(R.layout.fragment_detalle_equipo, container, false)
        eliminarBtn=v.findViewById(R.id.EliminarBtn)
        return v
    }

    override fun onStart() {
        super.onStart()
        abm = EquipoABM() // Inicializar la instancia de EquipoABM

        eliminarBtn.setOnClickListener {
            val idEquipo = "e87F5niWZNsBtb3GVclt" // Aquí deberías obtener el id que quieres eliminar.
            deleteDeviceById(idEquipo)
        }
    }

    private fun deleteDeviceById(idEquipo: String) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                viewModel.eliminarEquipo(idEquipo)
                Toast.makeText(context, "Equipo $idEquipo eliminado correctamente", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(context, "Error al eliminar el equipo: $e", Toast.LENGTH_SHORT).show()
            }
        }
    }

}