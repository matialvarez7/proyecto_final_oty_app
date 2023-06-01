package com.example.proyecto_final_oty_app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_final_oty_app.R
import com.example.proyecto_final_oty_app.entities.ItemPrestamo
import com.example.proyecto_final_oty_app.entities.Prestamo
import com.example.proyecto_final_oty_app.entities.PrestamoFinal

class AdapterPrestamo(var prestamos : MutableList<ItemPrestamo>/*, var onClick : (Int) -> Unit*/) : RecyclerView.Adapter<AdapterPrestamo.PrestamoHolder>(){

    class PrestamoHolder(v : View) : RecyclerView.ViewHolder(v)
    {
        private var view: View
        init {
            this.view = v
        }

        fun setPrueba1 (prueba : String) {
            val campoPrueba1 : TextView = view.findViewById(R.id.prueba1)
            campoPrueba1.text = prueba
        }

        fun setPrueba2 (prueba : String) {
            val campoPrueba2 : TextView = view.findViewById(R.id.prueba2)
            campoPrueba2.text = prueba
        }

        fun setResponsable (responsable : String){
            val txtResponsable : TextView = view.findViewById(R.id.Responsable)
            txtResponsable.text = responsable
        }

        fun setEquipos (equipos : String){
            val txtEquipos : TextView = view.findViewById(R.id.cantEquipos)
            txtEquipos.text = equipos
        }

        fun setEstadoPrestamo (estadoPrestamo : String){
            val txtEstadoPrestamo : TextView = view.findViewById(R.id.EstadoFinal)
            txtEstadoPrestamo.text = estadoPrestamo
        }

        fun getCard() : CardView {
            return view.findViewById(R.id.cardPrestamo)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrestamoHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_prestamo_item,parent, false)
        return PrestamoHolder(view)
    }

    override fun getItemCount(): Int {
        return prestamos.size
    }

    override fun onBindViewHolder(holder: PrestamoHolder, position: Int) {
        holder.setPrueba1(prestamos[position].idPrestamo)
        holder.setPrueba2(prestamos[position].idEquipo)

        /*
        val nombreCompleto = prestamos[position].nombre + " " + prestamos[position].apellido
        holder.setResponsable(nombreCompleto)
        holder.setEstadoPrestamo(prestamos[position].estadoPrestamo)
        holder.setEquipos((prestamos[position].itemsPrestamo.size).toString())
        holder.getCard().setOnClickListener {
            onClick(position)
        }*/
    }

}