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
    var personal: MutableList<Personal> = mutableListOf()
    var itemsPrestamo: MutableList<ItemPrestamo> = mutableListOf()
    var prestamoFinal: MutableList<PrestamoFinal> = mutableListOf()


    suspend fun inicializarColecciones() : MutableList<Prestamo>  {
        prestamos = mutableListOf()
        var basePrestamos = db.collection("prestamos").get().await()
        if (basePrestamos != null){
            prestamos = basePrestamos.toObjects<Prestamo>() as MutableList<Prestamo>
        }
        return prestamos

        /*Obtener toda la coleccion de préstamos
        db.collection("prestamos")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    prestamos.add(document.toObject())
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            } */

        /*Obtener toda la coleccion de personal
        db.collection("personal")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    personal.add(document.toObject())
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }*/

        /*Obtener toda la coleccion de itemsPrestamo
        db.collection("itemsPrestamo")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    itemsPrestamo.add(document.toObject())
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }*/
    }

    fun getListaPrueba () : MutableList<Prestamo> {
        return prestamos
    }

    fun armarListaFinal() {

        var personalAux: Personal
        var itemPrestamoAux: MutableList<ItemPrestamo> = mutableListOf()


        //Si la lista de prestamos no esta vacia, armo la lista final entregable para el adapter
        if (prestamos.isNotEmpty()) {

            for (prestamo in prestamos) {
                //Inicializo el objeto prestamo final para agregar al listado final
                val pFinalAux: PrestamoFinal = PrestamoFinal("",Date(2024, 1, 1), "", "", "",itemPrestamoAux)

                //Busco y obtengo el personal correspondiente al idPersonal del prestamo
                personalAux = personal.find { personal -> personal.id == prestamo.idPersonal}!!

                //Se arma lista de items correspondientes al prestamo
                for(item in itemsPrestamo){
                    if (item.idPrestamo == prestamo.id){
                        itemPrestamoAux.add(item)
                    }
                }
                //Construccion del prestamo final a mostrar en el recycler y pasar al detalle
                pFinalAux.idPrestamo = prestamo.id.toString()
                pFinalAux.fechaPrestamo = Date(prestamo.fechaPrestamo.time)
                Log.d("prueba fecha","La fecha es ${pFinalAux.fechaPrestamo}")
                pFinalAux.estadoPrestamo = prestamo.estadoPrestamo
                pFinalAux.nombre = personalAux.nombre
                pFinalAux.apellido = personalAux.apellido
                pFinalAux.itemsPrestamo = itemPrestamoAux

                //Finalmente, lo añado al listado final
                prestamoFinal.add(pFinalAux)
                        }
                    }
            }

        fun getListaFinal () : MutableList<PrestamoFinal> {
            return prestamoFinal
        }

}



