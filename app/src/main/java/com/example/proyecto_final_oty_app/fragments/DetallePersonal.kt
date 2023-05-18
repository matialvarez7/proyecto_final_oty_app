package com.example.proyecto_final_oty_app.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.proyecto_final_oty_app.R
import com.example.proyecto_final_oty_app.entities.Personal
import com.example.proyecto_final_oty_app.entities.PersonalABM
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetallePersonal : Fragment() {
    private lateinit var v:View
    private lateinit var eliminarBtn:Button
    private lateinit var abm: PersonalABM
    private val viewModel: DetallePersonalViewModel by viewModels()

    companion object {
        fun newInstance() = DetallePersonal()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v= inflater.inflate(R.layout.fragment_detalle_personal, container, false)
        eliminarBtn=v.findViewById(R.id.EliminarBtn)
        return v
    }

    override fun onStart() {
        super.onStart()
        abm = PersonalABM() // Inicializar la instancia de PersonalABM

        eliminarBtn.setOnClickListener {
            val idPersonal = "07x97ENsdukDtt5K2YyX" // Aquí deberías obtener el DNI que quieres eliminar.
            deleteUserByDNI(idPersonal)
        }
    }

    private fun deleteUserByDNI(idPersonal: String) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                viewModel.eliminarPersonal(idPersonal)
                Toast.makeText(context, "Usuario eliminado correctamente", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(context, "Error al eliminar usuario: $e", Toast.LENGTH_SHORT).show()
            }
        }
    }


}