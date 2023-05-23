package com.example.proyecto_final_oty_app.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.proyecto_final_oty_app.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar

class NewAsignacion : Fragment() {

    private lateinit var v: View
    lateinit var editDNI: EditText
    lateinit var editInventario: EditText
    lateinit var confirmarBtn: Button
    lateinit var buscarInventarioBtn: Button
    lateinit var buscarDNIBtn: Button

    companion object {
        fun newInstance() = NewAsignacion()
    }

    private lateinit var viewModel: NewAsignacionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_new_asignacion, container, false)
        editDNI = v.findViewById(R.id.editDNI)
        editInventario = v.findViewById(R.id.editInventario)
        confirmarBtn = v.findViewById(R.id.confirmarBtn)
        buscarDNIBtn = v.findViewById(R.id.buscarDNIBtn)
        buscarInventarioBtn = v.findViewById(R.id.buscarInventarioBtn)
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NewAsignacionViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onStart() {
        super.onStart()

        //"placeholders"
        editDNI.hint = "DNI"
        editInventario.hint = "Nro. Inventario"

        buscarDNIBtn.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                try {
                    val personalEncontrado = viewModel.buscarPersonalByDNI(editDNI.text.toString())
                    Toast.makeText(
                        context,
                        "El DNI corresponde a " + personalEncontrado.nombre + " " + personalEncontrado.apellido,
                        Toast.LENGTH_SHORT
                    ).show()
                } catch (e: Exception) {
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
        buscarInventarioBtn.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                try {
                    val equipoEncontrado =
                        viewModel.buscarEquipoByInventario(editInventario.text.toString())
                    Toast.makeText(
                        context,
                        "El Inventario corresponde al equipo " + equipoEncontrado.nombre,
                        Toast.LENGTH_SHORT
                    ).show()
                } catch (e: Exception) {
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
        confirmarBtn.setOnClickListener {
            val dniIngresado = editDNI.text.toString()
            val invenatrioIngresado = editInventario.text.toString()
            editDNI.text.clear()
            editInventario.text.clear()

            if (viewModel.camposValidos(invenatrioIngresado, dniIngresado)) {


                CoroutineScope(Dispatchers.Main).launch {
                    try {
                        val personalEncontrado = viewModel.buscarPersonalByDNI(dniIngresado)
                        val equipoEncontrado =
                            viewModel.buscarEquipoByInventario(invenatrioIngresado)
                        val idPersonalAsignar = personalEncontrado.id
                        val idEquipoAsignar = equipoEncontrado.id
                        if (!viewModel.elEquipoFueAsignado(idEquipoAsignar)) {
                            val fechaDeCreacion = Calendar.getInstance().time
                            if (idPersonalAsignar != null) {
                                val ok = viewModel.registrarAsignacion(
                                    idPersonalAsignar,
                                    idEquipoAsignar,
                                    fechaDeCreacion
                                )
                                if (ok) {
                                    Toast.makeText(
                                        context,
                                        "Asignacion registrada con éxito",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Lo sentimos, se produjo un error al conectarse al servidor.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        } else {
                            Toast.makeText(
                                context,
                                "El equipo ya ha sido asignado a otro miembro del personal",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } catch (e: Exception) {
                        Toast.makeText(context, "Revise los campos ingresados", Toast.LENGTH_SHORT)
                            .show()
                    }
                }//cierro corrutina anidada


            } else {
                Toast.makeText(
                    context,
                    "Es obligatorio que complete ambos campos.",
                    Toast.LENGTH_SHORT
                ).show()
            }//cierro camposValidos
        }
    }
}
