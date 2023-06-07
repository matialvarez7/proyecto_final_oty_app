package com.example.proyecto_final_oty_app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_final_oty_app.R
import com.example.proyecto_final_oty_app.entities.AsignacionDocente
import com.example.proyecto_final_oty_app.entities.Equipo
import com.example.proyecto_final_oty_app.entities.Personal

class AdapterAsignacion (var equipos: MutableList<Equipo>, var personal: MutableList<Personal>, var asignaciones: MutableList<AsignacionDocente>, var onClick : (Int) -> Unit) : RecyclerView.Adapter<AdapterAsignacion.MyViewHolder>(){

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterAsignacion.MyViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.asignacion_item,parent, false)
            return MyViewHolder(view)
        }



        override fun onBindViewHolder(holder: AdapterAsignacion.MyViewHolder, position: Int) {

            val asignacion : AsignacionDocente = asignaciones[position]
            val personal:Personal?=retornoDePersonal(asignacion.idPersonal)
            if (personal!=null) {
                holder.setAsignacionNombreApellido(personal.nombre+" "+personal.apellido)
            }
            else{
                holder.setAsignacionNombreApellido("Id No Encontrado")
            }

            val equipo :Equipo?=retornoDeEquipo(asignacion.idEquipo)
            if (equipo!=null) {
                holder.setEquipoNombre(equipo.nombre)
            }
            else{
                holder.setEquipoNombre("Id No Encontrado")
            }
            holder.getCard().setOnClickListener {
                onClick(position)
            }

        }

        private fun retornoDePersonal(idPersonal: String): Personal? {

            return personal.find { personal:Personal->personal.id.equals(idPersonal) }
        }

        private fun retornoDeEquipo(idEquipo: String):Equipo?{
            return equipos.find { equipo: Equipo -> equipo.id.equals(idEquipo) }

        }

        override fun getItemCount(): Int {

            return asignaciones.size
        }



        public class MyViewHolder(v : View) : RecyclerView.ViewHolder(v){

            private var view: View

            init {

                this.view = v
            }

            fun setAsignacionNombreApellido(inventario : String){
                val txtInventario : TextView = view.findViewById(R.id.NombreApellido)
                txtInventario.text = inventario

            }

            fun setEquipoNombre (nombre : String){
                val txtNombre : TextView = view.findViewById(R.id.NombreEquipos)
                txtNombre.text = nombre

            }

            fun getCard() : CardView {
                return view.findViewById(R.id.cardAsignacion)
            }


        }
    }

