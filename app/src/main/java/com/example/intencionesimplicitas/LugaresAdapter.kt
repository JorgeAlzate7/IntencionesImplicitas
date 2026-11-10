package com.example.intencionesimplicitas

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// adaptador para mostrar la lista de lugares con evento click
class LugaresAdapter(
    private val listaLugares: List<Lugar>,
    private val onItemClick: (Lugar) -> Unit
) : RecyclerView.Adapter<LugaresAdapter.LugarViewHolder>() {

    class LugarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgProducto: ImageView = itemView.findViewById(R.id.imgProducto)
        val tvNombreProducto: TextView = itemView.findViewById(R.id.tvNombreProducto)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LugarViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.elemento_lista, parent, false)
        return LugarViewHolder(view)
    }

    override fun onBindViewHolder(holder: LugarViewHolder, position: Int) {
        val lugar = listaLugares[position]
        holder.tvNombreProducto.text = lugar.nombre
        holder.imgProducto.setImageResource(lugar.imagen)

        // evento click en el item para enviar el lugar a la actividad detalle
        holder.itemView.setOnClickListener {
            onItemClick(lugar)
        }
    }

    override fun getItemCount(): Int {
        return listaLugares.size
    }
}
