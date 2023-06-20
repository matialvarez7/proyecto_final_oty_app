package com.example.proyecto_final_oty_app.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.proyecto_final_oty_app.R
import com.example.proyecto_final_oty_app.entities.Personal
import com.google.android.material.snackbar.Snackbar

class EditarDetallePersonal : Fragment() {

    lateinit var v:View
    lateinit var confirmarBtn: Button
    lateinit var editDNI:EditText
    lateinit var editNombre:EditText
    lateinit var editApellido:EditText
    lateinit var editArea:EditText
    private lateinit var builder : AlertDialog.Builder
    private lateinit var viewModel: EditarDetallePersonalViewModel
    companion object {
        fun newInstance() = EditarDetallePersonal()
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_editar_detalle_personal, container, false)
        confirmarBtn = v.findViewById(R.id.confirmarBtn)
        editDNI = v.findViewById(R.id.editDNI)
        editNombre = v.findViewById(R.id.editNombre)
        editApellido = v.findViewById(R.id.editApellido)
        editArea = v.findViewById(R.id.editArea)

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(EditarDetallePersonalViewModel::class.java)
        // TODO: Use the ViewModel
    }
    override fun onStart() {
        super.onStart()
        val personal = DetallePersonalArgs.fromBundle(requireArguments()).personal
        builder = AlertDialog.Builder(context)
        editDNI.setText(personal.dni.toString())
        editNombre.setText(personal.nombre.toString())
        editApellido.setText(personal.apellido.toString())
        editArea.setText(personal.area.toString())
        if(personal!=null) {
            confirmarBtn.setOnClickListener {
                builder.setMessage("¿ Desea Confirmar ?")
                    .setCancelable(true)
                    .setNegativeButton("No"){ dialogInterface, it ->
                        dialogInterface.cancel()
                    }
                    .setPositiveButton("Si"){dialogInterface,it->
                        var personalNuevo = Personal(personal.id,editDNI.text.toString() , editNombre.text.toString(), editApellido.text.toString(), editArea.text.toString())
                        viewModel.actualizarPersonal(personalNuevo)
                        Snackbar.make(v,"Se edito correctamente el usuario.",1000).show()
                        findNavController().navigateUp()
                    }

                    .show()

            }
        }
    }
}

