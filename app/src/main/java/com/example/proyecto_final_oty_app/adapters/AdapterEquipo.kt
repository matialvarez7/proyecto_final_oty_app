package com.example.proyecto_final_oty_app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_final_oty_app.R
import com.example.proyecto_final_oty_app.entities.Equipo

class AdapterEquipo(var equipos: MutableList<Equipo>, var onClick : (Int) -> Unit) : RecyclerView.Adapter<AdapterEquipo.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterEquipo.MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_equipo_item,parent, false)
        return MyViewHolder(view)
    }

        override fun onBindViewHolder(holder: AdapterEquipo.MyViewHolder, position: Int) {

            val equipo : Equipo = equipos[position]
            holder.setEquipoInventario(equipo.inventario)
            holder.setEquipoNombre(equipo.nombre)
            holder.getCard().setOnClickListener {
                onClick(position)
            }

        }

        override fun getItemCount(): Int {

            return equipos.size
        }



        public class MyViewHolder(v : View) : RecyclerView.ViewHolder(v){

            private var view: View

            init {

                this.view = v
            }

            fun setEquipoInventario (inventario : String){
                val txtInventario : TextView = view.findViewById(R.id.NombreEquipos)
                txtInventario.text = inventario

            }

            fun setEquipoNombre (nombre : String){
                val txtNombre : TextView = view.findViewById(R.id.NombreEquipo)
                txtNombre.text = nombre

            }

            fun getCard() : CardView {
                return view.findViewById(R.id.cardAsignacion)
            }


        }
    }


