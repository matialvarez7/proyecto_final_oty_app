package com.example.proyecto_final_oty_app.fragments

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.proyecto_final_oty_app.entities.ItemPrestamo
import com.example.proyecto_final_oty_app.entities.Personal
import com.example.proyecto_final_oty_app.entities.Prestamo
import com.example.proyecto_final_oty_app.entities.PrestamoFinal
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.sql.Date



class ListPrestamosViewModel : ViewModel() {
    private var db = Firebase.firestore

    lateinit var prestamos: MutableList<Prestamo>
    lateinit var personal: MutableList<Personal>
    lateinit var itemsPrestamo: MutableList<ItemPrestamo>
    lateinit var prestamoFinal : MutableList<PrestamoFinal>

    suspend fun inicializarColecciones(){
        Log.d("Iniciando colecciones","Inicio las colecciones")
        prestamoFinal = mutableListOf()

        //Obtener coleccion de prestamos
        prestamos = mutableListOf()
        var basePrestamos = db.collection("prestamos").get().await()
        if (basePrestamos != null){
            prestamos = basePrestamos.toObjects<Prestamo>() as MutableList<Prestamo>
        } else {
            Log.d(TAG, "Error getting documents")
        }
        //Obtener coleccion de personal
        personal = mutableListOf()
        var basePersonal = db.collection("personal").get().await()
        if (basePersonal != null){
            personal = basePersonal.toObjects<Personal>() as MutableList<Personal>
        } else {
            Log.d(TAG, "Error getting documents")
        }

        //Obtener coleccion de itemsPrestamo
        itemsPrestamo = mutableListOf()
        var baseItemsPrestamo = db.collection("itemsPrestamo").get().await()
        if (baseItemsPrestamo != null){
            itemsPrestamo = baseItemsPrestamo.toObjects<ItemPrestamo>() as MutableList<ItemPrestamo>
        } else {
            Log.d(TAG, "Error getting documents ")
        }
    }

       fun armarListaFinal() {

        prestamoFinal.clear()
        var personalAux : Personal?

        //Si la lista de prestamos no esta vacia, armo la lista final entregable para el adapter
        if (prestamos.isNotEmpty()) {
            for (prestamo in prestamos) {
                //Inicializo el objeto prestamo final para agregar al listado final
                var pFinalAux = PrestamoFinal("",Date(2024, 1, 1), "", "", "", mutableListOf())

                //Busco y obtengo el personal correspondiente al idPersonal del prestamo
                personalAux = personal.find { personal -> personal.id == prestamo.idPersonal}

                //Se arma lista de items correspondientes al prestamo
                for(item in itemsPrestamo){
                    if (item.idPrestamo == prestamo.id){
                        pFinalAux.itemsPrestamo.add(item)
                    }
                }

                //Construccion del prestamo final a mostrar en el recycler y pasar al detalle
                pFinalAux.idPrestamo = prestamo.id.toString()
                pFinalAux.fechaPrestamo = Date(prestamo.fechaPrestamo.time)
                pFinalAux.estadoPrestamo = prestamo.estadoPrestamo

                if (personalAux != null){
                    pFinalAux.nombre = personalAux.nombre
                    pFinalAux.apellido = personalAux.apellido
                } else {
                    pFinalAux.nombre = "Responsable "
                    pFinalAux.apellido = "no encontrado"
                }

                //Finalmente, lo añado al listado final
                prestamoFinal.add(pFinalAux)
                        }
                    }
            }

}
