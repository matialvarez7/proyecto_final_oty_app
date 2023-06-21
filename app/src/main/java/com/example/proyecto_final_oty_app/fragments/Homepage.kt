package com.example.proyecto_final_oty_app.fragments


import android.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.proyecto_final_oty_app.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Homepage : Fragment() {

    lateinit var v : View
    lateinit var btnAsigDocent : CardView
    lateinit var btnPrestamos : CardView
    lateinit var btnPersonal : CardView
    lateinit var btnEquipos : CardView
    lateinit var progreBarAsig : ProgressBar
    lateinit var progreBarPres : ProgressBar
    lateinit var db : FirebaseFirestore
    lateinit var cantAsign : TextView
    lateinit var cantPres : TextView
    lateinit var btnCerrarSesion: ImageButton
    lateinit var porcentAsignaciones : TextView
    lateinit var porcentPrestamos : TextView
    lateinit var viewModel: HomepageViewModel
    lateinit var user : TextView
    private lateinit var auth: FirebaseAuth
    private lateinit var builder : AlertDialog.Builder


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_homepage, container, false)
        auth = Firebase.auth
        btnAsigDocent = v.findViewById(R.id.botonAsignacionesDocentes)
        btnPrestamos = v.findViewById(R.id.botonPrestamos)
        btnPersonal = v.findViewById(R.id.botonPersonal)
        btnEquipos = v.findViewById(R.id.botonEquipos)
        progreBarAsig = v.findViewById(R.id.progressBarAsig)
        progreBarPres = v.findViewById(R.id.progressBarPrestamos)
        cantAsign = v.findViewById(R.id.cantidadAsignaciones)
        cantPres = v.findViewById(R.id.cantidadPrestamos)
        btnCerrarSesion = v.findViewById(R.id.buttonCS)
        porcentAsignaciones = v.findViewById(R.id.porcentajeAsignaciones)
        porcentPrestamos = v.findViewById(R.id.porcentajePrestamos)
        user = v.findViewById(R.id.User)

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomepageViewModel::class.java)
        // TODO: Use the ViewModel
    }


    override fun onStart() {
        super.onStart()
        db = FirebaseFirestore.getInstance()
        builder = AlertDialog.Builder(context)

        btnAsigDocent.setOnClickListener(){
            val action = HomepageDirections.actionHomepageToListAsignacionesDocentes()
            findNavController().navigate(action)
        }

        btnPrestamos.setOnClickListener(){
            val action = HomepageDirections.actionHomepageToListPrestamos()
            findNavController().navigate(action)
        }

        btnPersonal.setOnClickListener(){
            val action = HomepageDirections.actionHomepageToListPersonal()
            findNavController().navigate(action)
        }

        btnEquipos.setOnClickListener(){
            val action = HomepageDirections.actionHomepageToListEquipo2()
            findNavController().navigate(action)
        }

        btnCerrarSesion.setOnClickListener(){
            builder.setMessage("¿Estás seguro de que quieres cerrar sesión?")
                .setCancelable(true)
                .setPositiveButton("Cerrar sesión"){dialogInterface,it->
                    CoroutineScope(Dispatchers.Main).launch {
                        auth.signOut()
                        requireActivity().finish()
                    }
                }

                .show()


        }

        lifecycleScope.launch {
            viewModel.generarProgressBar("Asignaciones",cantAsign, porcentAsignaciones, progreBarAsig)
        }

        lifecycleScope.launch {
            viewModel.generarProgressBar("Prestamos",cantPres, porcentPrestamos, progreBarPres)
        }

        user.text = auth.currentUser?.email.toString()

    }

}