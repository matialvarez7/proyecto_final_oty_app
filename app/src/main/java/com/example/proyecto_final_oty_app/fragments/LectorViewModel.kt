package com.example.proyecto_final_oty_app.fragments

import android.content.Intent
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.google.zxing.integration.android.IntentIntegrator

class LectorViewModel : ViewModel() {

    val valorEscaneadoInventario : MutableLiveData<String> = MutableLiveData()
    //val valorEscaneado : LiveData<String> = _valorEscaneado

    val valorEscaneadoNombre : MutableLiveData<String> = MutableLiveData()
    val valorEscaneadoAnet : MutableLiveData<String> = MutableLiveData()

    fun setValor (valor : String, campo : Int){
        if(campo == 0){
            valorEscaneadoInventario.value = valor
        } else if (campo == 1) {
            valorEscaneadoNombre.value = valor
        } else {
            valorEscaneadoAnet.value = valor
        }

    }

    fun clearValor (){
        valorEscaneadoInventario.value = ""
        valorEscaneadoAnet.value = ""
        valorEscaneadoNombre.value = ""
    }

}