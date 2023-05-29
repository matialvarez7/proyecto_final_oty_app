package com.example.proyecto_final_oty_app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_final_oty_app.R
import com.example.proyecto_final_oty_app.entities.Prestamo

class AdapterPrestamo(var prestamos : MutableList<Prestamo>, var onClick : (Int) -> Unit) : RecyclerView.Adapter<AdapterPrestamo.PrestamoHolder>(){

    class PrestamoHolder(v : View) : RecyclerView.ViewHolder(v)
    {
        private var view: View
        init {
            this.view = v
        }

        fun setResponsable (responsable : String){
            val txtResponsable : TextView = view.findViewById(R.id.Responsable)
            txtResponsable.text = responsable
        }

        fun setEquipos (equipos : String){
            val txtEquipos : TextView = view.findViewById(R.id.Equipos)
            txtEquipos.text = equipos
        }

        fun setEstadoPrestamo (estadoPrestamo : String){
            val txtEstadoPrestamo : TextView = view.findViewById(R.id.EstadoPrestamo)
            txtEstadoPrestamo.text = estadoPrestamo
        }

        fun getCard() : CardView {
            return view.findViewById(R.id.cardPrestamo)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrestamoHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_prestamo_item,parent, false)
        return (PrestamoHolder(view))
    }

    override fun getItemCount(): Int {
        return prestamos.size
    }

    override fun onBindViewHolder(holder: PrestamoHolder, position: Int) {
        //holder.setResponsable(prestamos[position].)

        holder.getCard().setOnClickListener{
            onClick(position)
        }
    }

}