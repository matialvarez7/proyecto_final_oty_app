package com.example.proyecto_final_oty_app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_final_oty_app.R
import com.example.proyecto_final_oty_app.entities.Equipo

class PrestamoAdapter(var equipos : MutableList<Equipo>) : RecyclerView.Adapter<PrestamoAdapter.PrestamoHolder>() {



    class PrestamoHolder(v : View) : RecyclerView.ViewHolder(v){

        private var view : View
        init {
            this.view = v
        }

        fun setNumeroInventario(inventario : String){
            val nroIventario : TextView = view.findViewById( R.id.nroIventario)
            nroIventario.text = inventario
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
    }
}