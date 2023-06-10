package com.example.proyecto_final_oty_app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_final_oty_app.R
import com.example.proyecto_final_oty_app.entities.Personal

class AdapterPersonal (var personales: MutableList<Personal>, var onClick : (Int) -> Unit) : RecyclerView.Adapter<AdapterPersonal.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterPersonal.MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_personal_item,parent, false)
        return MyViewHolder(view)
    }



    override fun onBindViewHolder(holder: AdapterPersonal.MyViewHolder, position: Int) {

        val personal : Personal = personales[position]
        holder.setPersonalDni(personal.dni)
        holder.setPersonalNombre(personal.nombre)
        holder.setPersonalApellido(personal.apellido)
        holder.setPersonalArea(personal.area)

        holder.getCard().setOnClickListener {
            onClick(position)
        }

    }



    override fun getItemCount(): Int {

        return personales.size
    }



    public class MyViewHolder(v : View) : RecyclerView.ViewHolder(v){

        private var view: View

        init {

            this.view = v
        }

        fun setPersonalDni (dni : String){
            val txtDni : TextView = view.findViewById(R.id.dniPersonal)
            txtDni.text = dni

        }

        fun setPersonalNombre (nombre : String){
            val txtNombre : TextView = view.findViewById(R.id.nombrePersonal)
            txtNombre.text = nombre + ","

        }

        fun setPersonalArea (area : String){
            val txtArea : TextView = view.findViewById(R.id.areaPersonal)
            txtArea.text = area

        }

        fun setPersonalApellido (apellido : String){
            val txtApellido : TextView = view.findViewById(R.id.apellidoPersonal)
            txtApellido.text = apellido + " -"

        }

        fun getCard() : CardView {
            return view.findViewById(R.id.cardPersonal)
        }


    }
}

