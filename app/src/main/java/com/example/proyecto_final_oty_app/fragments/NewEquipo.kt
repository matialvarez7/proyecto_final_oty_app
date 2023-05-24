package com.example.proyecto_final_oty_app.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.proyecto_final_oty_app.R
import com.google.android.material.snackbar.Snackbar
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import kotlinx.coroutines.launch

/*
import com.google.zxing.integration.android.IntentIntegrator
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.proyecto_final_oty_app.MainActivity
import com.journeyapps.barcodescanner.ScanOptions */

class NewEquipo : Fragment() {


    companion object {
        fun newInstance() = NewEquipo()
    }

    lateinit var v : View
    private lateinit var viewModel: NewEquipoViewModel
    lateinit var btnConfirmar : Button
    lateinit var nroInventario : EditText // recibe el número de inventario de la vista
    lateinit var nroEquipo : EditText // recibe el número de equipo de la vista
    lateinit var nroAnet : EditText // recibe el número de A-NET de la vista
    lateinit var titulo : TextView // recibe el titulo del fragment
    lateinit var scanInventario : ImageButton
    lateinit var scanNombre : ImageButton
    lateinit var scanAnet : ImageButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_new_equipo, container, false)

        // bindeamos los componentes de las vistas para poder usarlos
        nroInventario = v.findViewById(R.id.nroInventario)
        nroEquipo = v.findViewById(R.id.nroEquipo)
        nroAnet = v.findViewById(R.id.nroAnet)
        btnConfirmar = v.findViewById(R.id.editar1)
        titulo = v.findViewById(R.id.titulo)
        titulo.text = "Alta equipo"
        scanInventario = v.findViewById(R.id.btnEscInventario)
        scanNombre = v.findViewById(R.id.btnEscEquipo)
        scanAnet = v.findViewById(R.id.btnEscAnet)
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NewEquipoViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onStart() {
        super.onStart()

        //Configuracion de escaner
        val options = GmsBarcodeScannerOptions.Builder()
            .setBarcodeFormats(
                Barcode.FORMAT_CODE_128,
                Barcode.FORMAT_CODE_93,
                Barcode.FORMAT_CODE_39
                        ).build()
        val scanner = GmsBarcodeScanning.getClient (requireContext(), options)


        //Boton escaneo de inventario
        scanInventario.setOnClickListener{
            scanner.startScan()
                .addOnSuccessListener { barcode ->
                    val rawValue: String? = barcode.rawValue
                    nroInventario.setText(rawValue)
                }
                .addOnCanceledListener {
                    Toast.makeText(requireContext(), "Scanner cancelado", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(requireContext(), "Error: $e.toString()", Toast.LENGTH_SHORT).show()
                }
        }

        //Boton escaneo de nombre de equipo
        scanNombre.setOnClickListener{
            scanner.startScan()
                .addOnSuccessListener { barcode ->
                    val rawValue: String? = barcode.rawValue
                    nroEquipo.setText(rawValue)
                }
                .addOnCanceledListener {
                    Toast.makeText(requireContext(), "Scanner cancelado", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(requireContext(), "Error: $e.toString()", Toast.LENGTH_SHORT).show()
                }
        }

        //Boton escaneo de anet
        scanAnet.setOnClickListener{
            scanner.startScan()
                .addOnSuccessListener { barcode ->
                    val rawValue: String? = barcode.rawValue
                    nroAnet.setText(rawValue)
                }
                .addOnCanceledListener {
                    Toast.makeText(requireContext(), "Scanner cancelado", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(requireContext(), "Error: $e.toString()", Toast.LENGTH_SHORT).show()
                }
        }

        btnConfirmar.setOnClickListener {
            if(!viewModel.formularioValido(nroInventario.text.toString(), nroEquipo.text.toString(), nroAnet.text.toString())){
                Snackbar.make(v, "Debe completar todos los campos", Snackbar.LENGTH_LONG).show()
            }else{
                lifecycleScope.launch {
                    if (viewModel.equipoExistente(nroInventario.text.toString())) {
                        Toast.makeText(requireContext(), "El equipo ya existe.", Toast.LENGTH_SHORT).show()
                    } else {
                        viewModel.altaNuevoEquipo(
                            nroInventario.text.toString(),
                            nroEquipo.text.toString(),
                            nroAnet.text.toString()
                        )

                        nroInventario.text.clear()
                        nroEquipo.text.clear()
                        nroAnet.text.clear()

                        findNavController().popBackStack()
                    }
                }
            }

        }
    }

}