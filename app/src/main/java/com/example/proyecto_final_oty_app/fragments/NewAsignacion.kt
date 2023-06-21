package com.example.proyecto_final_oty_app.fragments

import android.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.proyecto_final_oty_app.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar

class NewAsignacion : Fragment() {

    private lateinit var v: View
    private val sharedViewModel : LectorViewModel by activityViewModels()
    lateinit var editDNI: EditText
    lateinit var editInventario: EditText
    lateinit var confirmarBtn: Button
    lateinit var buscarInventarioBtn: Button
    lateinit var buscarDNIBtn: Button
    lateinit var nombreCompletoText: EditText
    lateinit var areaText: EditText
    lateinit var nombreEquipoText: EditText
    lateinit var lector : ImageButton

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
        editInventario = v.findViewById(R.id.editInventario2)
        confirmarBtn = v.findViewById(R.id.confirmarBtn)
        buscarDNIBtn = v.findViewById(R.id.buscarDNIBtn)
        buscarInventarioBtn = v.findViewById(R.id.buscarInventarioBtn)
        nombreCompletoText = v.findViewById(R.id.editResponsable)
        areaText = v.findViewById(R.id.editAreaAsig)
        nombreEquipoText = v.findViewById(R.id.editNombreEquipo)
        lector = v.findViewById(R.id.scanInventarioAsignacion)
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
        //editDNI.hint = "DNI"
        //editInventario.hint = "Nro. Inventario"

        lector.setOnClickListener {
            val action = NewAsignacionDirections.actionNewAsignacionToLector(0)
            findNavController().navigate(action)
        }
        sharedViewModel.valorEscaneadoInventario.observe(viewLifecycleOwner, Observer { valor ->
            editInventario.setText(valor)
        })

        buscarDNIBtn.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                try {
                    val personalEncontrado = viewModel.buscarPersonalByDNI(editDNI.text.toString())
                    nombreCompletoText.setText(personalEncontrado.nombre + " " + personalEncontrado.apellido)
                    areaText.setText(personalEncontrado.area)

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
                    nombreEquipoText.setText(equipoEncontrado.nombre)

                } catch (e: Exception) {
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
        confirmarBtn.setOnClickListener {
            val dniIngresado = editDNI.text.toString()
            val invenatrioIngresado = editInventario.text.toString()

            AlertDialog.Builder(context).setTitle("Confirmar asignacion").setMessage("¿Desea confirmar la asignación?")
                .setPositiveButton(android.R.string.ok) { _, _ ->

                    if (viewModel.camposValidos(invenatrioIngresado, dniIngresado)) {
                        CoroutineScope(Dispatchers.Main).launch {
                            try {
                                val personalEncontrado = viewModel.buscarPersonalByDNI(dniIngresado)
                                val equipoEncontrado =
                                    viewModel.buscarEquipoByInventario(invenatrioIngresado)
                                val idPersonalAsignar = personalEncontrado.id
                                val idEquipoAsignar = equipoEncontrado.id
                                val fechaDeCreacion = Calendar.getInstance().time
                                if (equipoEncontrado.estado == "Disponible" && equipoEncontrado.nombre.contains("PROFH",ignoreCase = true)) {
                                    viewModel.registrarAsignacion(
                                        idPersonalAsignar,
                                        idEquipoAsignar,
                                        fechaDeCreacion
                                    )
                                    Toast.makeText(
                                        context,
                                        "Asignacion registrada con éxito",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    findNavController().navigateUp()
                                } else {
                                    Toast.makeText(
                                        context,
                                        "El equipo no esta disponible para su asignacion",
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
                .setNegativeButton(android.R.string.cancel, null)
                .show()
        }
    }
}