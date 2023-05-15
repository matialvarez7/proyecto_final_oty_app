package com.example.proyecto_final_oty_app.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.proyecto_final_oty_app.R
import com.example.proyecto_final_oty_app.entities.Equipo
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Text


class DetalleEquipo : Fragment() {

    companion object {
        fun newInstance() = DetalleEquipo()
    }

    private lateinit var viewModel: DetalleEquipoViewModel
    lateinit var v : View
    lateinit var numInv : TextView
    lateinit var nomEquipo : TextView
    lateinit var numAnet : TextView
    lateinit var estado : TextView
    val db = Firebase.firestore


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_detalle_equipo, container, false)
        numInv = v.findViewById(R.id.baseInventario)
        nomEquipo = v.findViewById((R.id.baseNombreEquipo))
        numAnet = v.findViewById(R.id.baseNumeroAnet)
        estado = v.findViewById(R.id.baseEstado)

        return v
    }

    /* override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetalleEquipoViewModel::class.java)
        // TODO: Use the ViewModel
    } */

    override fun onStart() {
        super.onStart()
        val docRef = db.collection("equipos").document("2")
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val equipo  = document.toObject<Equipo>()
                    numInv.text = equipo?.inventario
                    nomEquipo.text = equipo?.nombre
                    numAnet.text = equipo?.anet
                    estado.text = equipo?.estado
                    Log.d("Equipo encontrado", "DocumentSnapshot data: ${equipo.toString()}")
                } else {
                    Log.d("Error", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("error", "get failed with ", exception)
            }
    }

}