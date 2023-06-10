package com.example.proyecto_final_oty_app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_final_oty_app.R
import com.example.proyecto_final_oty_app.entities.Equipo

class AdapterDetallePrestamo (var equipos : MutableList<Equipo>, var onClick : (Int) -> Unit) : RecyclerView.Adapter<AdapterDetallePrestamo.DetallePrestamoHolder>() {

    class DetallePrestamoHolder(v: View) : RecyclerView.ViewHolder(v) {
        private var view: View

        init {
            this.view = v
        }

        fun setNombreEquipo(nombreEquipo : String) {
            var txtNombreEquipo : TextView = view.findViewById(R.id.nombreEquipoPrestamo)
            txtNombreEquipo.text = nombreEquipo
        }

        fun setEstadoEquipo(estado : String) {
            var txtEstado : TextView = view.findViewById(R.id.estadoEquipoPrestamo)
            txtEstado.text = estado
        }

        fun getCard(): CardView {
            return view.findViewById(R.id.cardDetallePrestamo)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): DetallePrestamoHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_detalle_prestamo_item, parent, false)
        return DetallePrestamoHolder(view)
    }

    override fun getItemCount(): Int {
        return equipos.size
    }

    override fun onBindViewHolder(holder: DetallePrestamoHolder, position: Int) {
        holder.setEstadoEquipo(equipos[position].estado)
        holder.setNombreEquipo(equipos[position].nombre)
        holder.getCard().setOnClickListener {
            onClick(position)
        }
    }
}