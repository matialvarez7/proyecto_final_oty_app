package com.example.proyecto_final_oty_app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_final_oty_app.R
import com.example.proyecto_final_oty_app.entities.Equipo
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AdapterCrearPrestamo(var equipos : MutableList<Equipo>) : RecyclerView.Adapter<AdapterCrearPrestamo.PrestamoHolder>() {



    class PrestamoHolder(v : View) : RecyclerView.ViewHolder(v){

        private var view : View
        val eliminarEquipoBtn : ImageButton = v.findViewById(R.id.eliminarEquipoBtn)
        init {
            this.view = v
        }

        fun setNumeroInventario(inventario : String){
            val nroIventario : TextView = view.findViewById( R.id.nroIventario)
            nroIventario.text = "Numero de inventario $inventario"
        }

    }

    // Instancia el Holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrestamoHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.equipo_prestamo_item,parent,false)
        return (PrestamoHolder(view))
    }

    // Retorna el tamaño de la lista
    override fun getItemCount(): Int {
        return equipos.size

    }

    override fun onBindViewHolder(holder: PrestamoHolder, position: Int) {
        holder.setNumeroInventario(equipos[position].inventario)
        holder.eliminarEquipoBtn.setOnClickListener(){
            equipos.remove(equipos[position])
        }
    }
}