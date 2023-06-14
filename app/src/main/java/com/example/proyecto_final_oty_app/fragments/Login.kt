package com.example.proyecto_final_oty_app.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.proyecto_final_oty_app.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class Login : Fragment() {

    private lateinit var viewModel: LoginViewModel
    lateinit var editEmail:EditText
    lateinit var editPassword:EditText
    lateinit var mensajeError:TextView
    lateinit var loginButton:Button
    private lateinit var auth: FirebaseAuth
    lateinit var v:View


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_login, container, false)
        auth = Firebase.auth
        editEmail=v.findViewById(R.id.editMail)
        editPassword=v.findViewById(R.id.editPassword)
        mensajeError=v.findViewById(R.id.mensajeError)
        loginButton=v.findViewById(R.id.loginButton)
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        // TODO: Use the ViewModel
        if(auth.currentUser!=null){
            val action = LoginDirections.actionLoginToMainActivity()
            findNavController().navigate(action)
        }
    }

    override fun onStart() {
        super.onStart()

        loginButton.setOnClickListener{
            lifecycleScope.launch {
                try{
                    auth.signInWithEmailAndPassword(editEmail.text.toString(),editPassword.text.toString()).await()
                    val action = LoginDirections.actionLoginToMainActivity()
                    findNavController().navigate(action)
                    }catch (e: Exception){
                    Toast.makeText(requireContext(), "Revise los datos ingresados.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }}
