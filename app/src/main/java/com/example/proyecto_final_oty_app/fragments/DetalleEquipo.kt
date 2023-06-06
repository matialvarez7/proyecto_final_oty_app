package com.example.proyecto_final_oty_app.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.proyecto_final_oty_app.R
import android.widget.Toast
import com.example.proyecto_final_oty_app.entities.EquipoABM
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//import kotlin.coroutines.jvm.internal.CompletedContinuation.context

class DetalleEquipo : Fragment() {

    lateinit var v: View

    lateinit var viewModel: DetalleEquipoViewModel
    lateinit var numInv: TextView
    lateinit var nomEquipo: TextView
    lateinit var numAnet: TextView
    lateinit var estado: TextView
    lateinit var editar:Button
    lateinit var eliminarBtn: Button
    lateinit var abm: EquipoABM

    companion object {
        fun newInstance() = DetalleEquipo()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        v = inflater.inflate(R.layout.fragment_detalle_equipo, container, false)
        numInv = v.findViewById(R.id.inventarioDetPrestEquipo)
        nomEquipo = v.findViewById((R.id.nombreDetPrestEquipo))
        numAnet = v.findViewById(R.id.anetDetPrestEquipo)
        estado = v.findViewById(R.id.estadoDetPrestEquipo)
        editar=v.findViewById(R.id.editarDetPrestEquipo)
        eliminarBtn=v.findViewById(R.id.EliminarBtn)

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetalleEquipoViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onStart() {
        super.onStart()
        abm = EquipoABM() // Inicializar la instancia de EquipoABM


        val equipo = DetalleEquipoArgs.fromBundle(requireArguments()).equipo

        numInv.text = equipo.inventario
        nomEquipo.text = equipo.nombre
        numAnet.text = equipo.anet
        estado.text = equipo.estado
        editar.setOnClickListener(){
            val action =DetalleEquipoDirections.actionDetalleEquipoToEditDetalleEquipo(equipo)
            findNavController().navigate(action)

        }
        eliminarBtn.setOnClickListener {
            val idEquipo = equipo.id // Aquí deberías obtener el id que quieres eliminar.
            CoroutineScope(Dispatchers.Main).launch {
            try{
                viewModel.eliminarEquipo(idEquipo)
                Toast.makeText(context, "Equipo $idEquipo eliminado correctamente", Toast.LENGTH_SHORT).show()

                findNavController().popBackStack()
            }catch(e:Exception){
                Toast.makeText(context, "Error al eliminar el equipo: $e", Toast.LENGTH_SHORT).show()
            }
            }
        }

}//cierra OnStart
}

    /*private fun deleteDeviceById(idEquipo: String) {
    CoroutineScope(Dispatchers.Main).launch {
        try {
            val viewModel = DetalleEquipoViewModel()
            viewModel.eliminarEquipo(idEquipo)
            Toast.makeText(context, "Equipo $idEquipo eliminado correctamente", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(context, "Error al eliminar el equipo: $e", Toast.LENGTH_SHORT).show()
            Toast.makeText(withContext(), "equipo eliminado correctamente", Toast.LENGTH_SHORT).show()
        }
    }*/


