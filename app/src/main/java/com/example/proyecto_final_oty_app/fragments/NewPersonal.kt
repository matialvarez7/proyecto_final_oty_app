package com.example.proyecto_final_oty_app.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.lifecycleScope
import com.example.proyecto_final_oty_app.R
import com.example.proyecto_final_oty_app.entities.Personal
import com.example.proyecto_final_oty_app.entities.PersonalABM
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class NewPersonal : Fragment() {
    private lateinit var abm: PersonalABM
    private lateinit var view: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
         view = inflater.inflate(R.layout.fragment_new_personal, container, false)
        return view
    }

    override fun onStart() {
        super.onStart()
        abm = PersonalABM() // Inicializar la instancia de PersonalABM

        val dniEditText: EditText = view.findViewById(R.id.dniEditText)
        val nombreEditText: EditText = view.findViewById(R.id.nombreEditText)
        val apellidoEditText: EditText = view.findViewById(R.id.apellidoEditText)
        val areaEditText: EditText = view.findViewById(R.id.areaEditText)
        val confirmarCargaButton: Button = view.findViewById(R.id.confirmarCargaButton)

        fun camposValidos(): Boolean {
            if (dniEditText.text.toString().trim().isEmpty()) {
                dniEditText.error = "DNI requerido"
                return false
            }

            if (nombreEditText.text.toString().trim().isEmpty()) {
                nombreEditText.error = "Nombre requerido"
                return false
            }

            if (apellidoEditText.text.toString().trim().isEmpty()) {
                apellidoEditText.error = "Apellido requerido"
                return false
            }

            if (areaEditText.text.toString().trim().isEmpty()) {
                areaEditText.error = "Área requerida"
                return false
            }

            return true
        }



        confirmarCargaButton.setOnClickListener() {
            if (camposValidos()) {
                val dni = dniEditText.text.toString()
                abm.existeDni(dni) { existe ->
                    if (!existe) {
                        val personal = Personal(
                            dni = dniEditText.text.toString(),
                            nombre = nombreEditText.text.toString(),
                            apellido = apellidoEditText.text.toString(),
                            area = areaEditText.text.toString()
                        )

                        viewLifecycleOwner.lifecycleScope.launch {
                            abm.agregarPersonal(personal)

                            Snackbar.make(
                                view,
                                "Personal agregado correctamente.",
                                Snackbar.LENGTH_LONG
                            ).show()
                        }


                        // Limpiar los campos de texto
                        dniEditText.text.clear()
                        nombreEditText.text.clear()
                        apellidoEditText.text.clear()
                        areaEditText.text.clear()

                    } else {
                        Snackbar.make(view, "El DNI ya existe", Snackbar.LENGTH_LONG).show()
                    }
                }
            }
        }

    }
}