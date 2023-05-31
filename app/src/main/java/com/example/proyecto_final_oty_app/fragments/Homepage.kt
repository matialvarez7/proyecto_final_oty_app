package com.example.proyecto_final_oty_app.fragments

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.fragment.findNavController
import com.example.proyecto_final_oty_app.R
import com.example.proyecto_final_oty_app.entities.Equipo
import com.example.proyecto_final_oty_app.entities.Personal
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import org.checkerframework.framework.qual.DefaultQualifier
import java.lang.Exception
import kotlin.math.roundToInt

class Homepage : Fragment() {

    lateinit var v : View
    lateinit var txtTitle : TextView
    lateinit var btnAsigDocent : CardView
    lateinit var btnPrestamos : CardView
    lateinit var btnPersonal : CardView
    lateinit var btnEquipos : CardView
    lateinit var progreBarAsig : ProgressBar
    lateinit var progreBarPres : ProgressBar
    lateinit var db : FirebaseFirestore
    lateinit var asignaciones : MutableList<Personal>  //Cambiar por Asignaciones
    lateinit var EquiposAsignDocentes : MutableList<Personal> //Equipos de Asignaciones
    lateinit var prestamos : MutableList<Personal>  //Cambiar por Asignaciones
    lateinit var EquiposPrestamos : MutableList<Personal> //Equipos de Prestamos
    lateinit var cantAsign : TextView
    lateinit var cantPres : TextView
    lateinit var btnCerrarSesion: ImageButton
    lateinit var porcentAsignaciones : TextView
    lateinit var porcentPrestamos : TextView





    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_homepage, container, false)
        txtTitle = v.findViewById(R.id.titleMenu)
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

        return v
    }

    override fun onStart() {
        super.onStart()
        db = FirebaseFirestore.getInstance()
        asignaciones = mutableListOf()
        EquiposAsignDocentes = mutableListOf()
        prestamos = mutableListOf()
        EquiposPrestamos = mutableListOf()

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
            val action = HomepageDirections.actionHomepageToListEquipo2() //Modificar aca para que cierre sesion.
            findNavController().navigate(action)
        }

        generarProgressBarAsignaciones()

        generarProgressBarPrestamos()

        //generarProgressBar2("personal", "personal","apellido", "Spinetta")

    }


    //USAR ESTA FUNCION CUANDO TENGA LOS OBJETOS Y BASE DE DATOS DE LAS ENTIDADES CORRESPONDIENTES.

    private fun generarProgressBar(nombreColeccionEquipos : String, nombreColeccionAoP : String, fieldEquipos : String, valueEquipos : String){
        var promedio : Float = 0F
        var cantTotalEquipos : Float
        var cantEquiposEnCurso : Float =0F
        db.collection(nombreColeccionEquipos).whereEqualTo(fieldEquipos,valueEquipos)//Me traigo todos los equipos segun corresponda a prestamos o asignaciones.
            .get()
            .addOnSuccessListener { snapshot ->
                if (snapshot != null) {
                    for (document in snapshot) {
                        EquiposAsignDocentes.add(document.toObject())

                    }
                    cantTotalEquipos = (EquiposAsignDocentes.size).toFloat()

                   db.collection(nombreColeccionAoP) // Quiero traerme asignaciones o prestamos en curso. Si esta el estado finalizados, Agregar el whereEqualTo.
                        .get()
                        .addOnSuccessListener { snapshot ->
                            if (snapshot != null && nombreColeccionAoP == "personales") { //Aca iria Prestamos
                                for (document in snapshot) {
                                    prestamos.add(document.toObject())

                                }
                            } else if (snapshot != null && nombreColeccionAoP == "personal"){ //Aca iria Asignaciones
                                for (document in snapshot) {
                                    asignaciones.add(document.toObject())

                                }
                            }

                            if(nombreColeccionAoP == "personales"){ //Aca iria Prestamos
                                cantEquiposEnCurso = (prestamos.size).toFloat()
                                cantPres.text = "${cantEquiposEnCurso.roundToInt()} / ${cantTotalEquipos.roundToInt()}"


                            } else if(nombreColeccionAoP == "personal"){ //Aca iria Asignaciones
                                cantEquiposEnCurso = (asignaciones.size).toFloat()
                                cantAsign.text = "${cantEquiposEnCurso.roundToInt()} / ${cantTotalEquipos.roundToInt()}"
                            }


                            if(cantTotalEquipos > 0){
                                promedio =(((cantEquiposEnCurso/cantTotalEquipos)* 100))
                            }


                            progreBarAsig.setProgress(promedio.roundToInt())

                            if(nombreColeccionAoP == "personales"){ //Aca iria Prestamos
                                porcentPrestamos.text = "${(promedio).roundToInt()} %"

                            } else if(nombreColeccionAoP == "personal"){ //Aca iria Asignaciones
                                porcentAsignaciones.text = "${(promedio).roundToInt()} %"
                            }


                        }
                        .addOnFailureListener { exception ->
                            Log.w(ContentValues.TAG, "Error getting documents: ", exception)
                        }
                }

            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents: ", exception)
            }
    }


    private fun generarProgressBar2(nombreColeccionEquipos : String, nombreColeccionAoP : String, fieldEquipos : String, valueEquipos : String){
        var promedio : Float = 0F
        var cantTotalEquipos : Float
        var cantEquiposEnCurso : Float =0F
        db.collection(nombreColeccionEquipos).whereEqualTo(fieldEquipos,valueEquipos)//Me traigo todos los equipos segun corresponda a prestamos o asignaciones.
            .get()
            .addOnSuccessListener { snapshot ->
                if (snapshot != null) {
                    for (document in snapshot) {
                        EquiposAsignDocentes.add(document.toObject())

                    }
                    cantTotalEquipos = (EquiposAsignDocentes.size).toFloat()

                    db.collection(nombreColeccionAoP) // Quiero traerme asignaciones o prestamos en curso. Si esta el estado finalizados, Agregar el whereEqualTo.
                        .get()
                        .addOnSuccessListener { snapshot ->
                            if (snapshot != null && nombreColeccionAoP == "personales") { //Aca iria Prestamos
                                for (document in snapshot) {
                                    prestamos.add(document.toObject())

                                }
                            } else if (snapshot != null && nombreColeccionAoP == "personal"){ //Aca iria Asignaciones
                                for (document in snapshot) {
                                    asignaciones.add(document.toObject())

                                }
                            }

                            if(nombreColeccionAoP == "personales" && cantTotalEquipos > 0){ //Aca iria Prestamos
                                cantEquiposEnCurso = (prestamos.size).toFloat()
                                cantPres.text = "${cantEquiposEnCurso.roundToInt()} / ${cantTotalEquipos.roundToInt()}"
                                promedio =(((cantEquiposEnCurso/cantTotalEquipos)* 100))
                                progreBarPres.setProgress(promedio.roundToInt())
                                porcentPrestamos.text = "${(promedio).roundToInt()} %"

                            } else if(nombreColeccionAoP == "personal" && cantTotalEquipos > 0){ //Aca iria Asignaciones
                                cantEquiposEnCurso = (asignaciones.size).toFloat()
                                cantAsign.text = "${cantEquiposEnCurso.roundToInt()} / ${cantTotalEquipos.roundToInt()}"
                                promedio =(((cantEquiposEnCurso/cantTotalEquipos)* 100))
                                progreBarAsig.setProgress(promedio.roundToInt())
                                porcentAsignaciones.text = "${(promedio).roundToInt()} %"

                            }


                        }
                        .addOnFailureListener { exception ->
                            Log.w(ContentValues.TAG, "Error getting documents: ", exception)
                        }
                }

            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents: ", exception)
            }
    }


    private fun generarProgressBarAsignaciones(){

        var promedioAsignaciones : Float
        var cantTotalAsignaciones : Float
        var cantAsignacionesEnCurso : Float
        db.collection("personal")//Con esto traigo todos los documentos
            .get()
            .addOnSuccessListener { snapshot ->
                if (snapshot != null) {
                    for (personal in snapshot) {
                        EquiposAsignDocentes.add(personal.toObject())

                    }
                    cantTotalAsignaciones = (EquiposAsignDocentes.size).toFloat()

                    db.collection("personal").whereEqualTo("apellido", "Spinetta")//Con esto traigo varios documentos
                        .get()//Con el whereNotEqualTo trae todos los datos que no tenga ese tipo de valor. Serian los no APROF o los otros.
                        .addOnSuccessListener { snapshot ->
                            if (snapshot != null) {
                                for (personal in snapshot) {
                                    asignaciones.add(personal.toObject())

                                }

                                cantAsignacionesEnCurso = (asignaciones.size).toFloat()


                                cantAsign.text = "${cantAsignacionesEnCurso.roundToInt()} / ${cantTotalAsignaciones.roundToInt()}" // VER PORQUE SI LO PONGO ABAJO DE ESTO NO FUNCIONA, CREO QUE ES POR LA CORRUTINA.

                                promedioAsignaciones =(((cantAsignacionesEnCurso/cantTotalAsignaciones)* 100))
                                progreBarAsig.setProgress(promedioAsignaciones.roundToInt())
                                porcentAsignaciones.text = "${(promedioAsignaciones).roundToInt()} %"
                            }
                        }
                        .addOnFailureListener { exception ->
                            Log.w(ContentValues.TAG, "Error getting documents: ", exception)
                        }
                }

            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents: ", exception)
            }
    }

    private fun generarProgressBarPrestamos(){
        var promedioPrestamos : Float
        var cantTotalPrestamos : Float
        var cantPrestamosEnCurso : Float

        db.collection("personal")//Con esto traigo todos los documentos
            .get()
            .addOnSuccessListener { snapshot ->
                if (snapshot != null) {
                    for (personal in snapshot) {
                        EquiposPrestamos.add(personal.toObject())

                    }
                    cantTotalPrestamos = (EquiposPrestamos.size).toFloat()

                    db.collection("personal").whereEqualTo("area", "Musica")//Con esto traigo varios documentos
                        .get()
                        .addOnSuccessListener { snapshot ->
                            if (snapshot != null) {
                                for (personal in snapshot) {
                                    prestamos.add(personal.toObject())

                                }

                                cantPrestamosEnCurso = (prestamos.size).toFloat()

                                cantPres.text = "${cantPrestamosEnCurso.roundToInt()} / ${cantTotalPrestamos.roundToInt()}" // VER PORQUE SI LO PONGO ABAJO DE ESTO NO FUNCIONA, CREO QUE ES POR LA CORRUTINA.

                                promedioPrestamos =(((cantPrestamosEnCurso/cantTotalPrestamos)* 100))
                                progreBarPres.setProgress(promedioPrestamos.roundToInt())
                                porcentPrestamos.text = "${(promedioPrestamos).roundToInt()} %"
                            }
                        }
                        .addOnFailureListener { exception ->
                            Log.w(ContentValues.TAG, "Error getting documents: ", exception)
                        }
                }

            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents: ", exception)
            }

        // MINUTO 16:31 DEL VIDEO DE BOTON BAR, POR LAS JERARQUIAS SI VUELVO A TOCAR MENU NO PUEDO VOLVER A LA PRINCIPAL, SI NO AL FRAGMENT QUE ESTABA ANTES.


    }
}






