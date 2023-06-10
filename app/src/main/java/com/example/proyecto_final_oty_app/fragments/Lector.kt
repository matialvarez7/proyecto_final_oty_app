package com.example.proyecto_final_oty_app.fragments

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.proyecto_final_oty_app.R
import com.google.zxing.integration.android.IntentIntegrator

@Suppress("DEPRECATION")
class Lector : Fragment() {

    companion object {
        fun newInstance() = Lector()
    }

    private val viewModel: LectorViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_lector, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //viewModel = ViewModelProvider(this).get(LectorViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onStart() {
        super.onStart()
        initScanner()
    }

    private fun initScanner() {
        IntentIntegrator.forSupportFragment(this).initiateScan();  //(requireContext() as Activity?).  initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        val tipoCampo : LectorArgs by navArgs()
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(requireContext(), "Cancelado", Toast.LENGTH_LONG).show()
            } else {
                viewModel.setValor(result.contents,tipoCampo.campo)
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }

        findNavController().popBackStack()
    }


}