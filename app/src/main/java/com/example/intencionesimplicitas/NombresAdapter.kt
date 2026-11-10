package com.example.intencionesimplicitas

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// adaptador para gestionar la lista de nombres en el recyclerview
class NombresAdapter(private val listaNombres: List<String>) :
    RecyclerView.Adapter<NombresAdapter.NombresViewHolder>() {

    // vista individual que contiene el textview de cada item
    class NombresViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNombre: TextView = itemView.findViewById(R.id.tvNombre)
    }

    // infla el diseno de item_nombre para cada elemento
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NombresViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_nombre, parent, false)
        return NombresViewHolder(view)
    }

    // asigna el texto de la lista al textview del elemento actual
    override fun onBindViewHolder(holder: NombresViewHolder, position: Int) {
        holder.tvNombre.text = listaNombres[position]
    }

    // devuelve la cantidad total de elementos en la lista
    override fun getItemCount(): Int {
        return listaNombres.size
    }
}
