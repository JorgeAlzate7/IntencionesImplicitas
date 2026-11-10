package com.example.intencionesimplicitas

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// adaptador para la lista dinamica del ejercicio 4
class DinamicoAdapter(
    private val listaItems: List<String>,
    private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<DinamicoAdapter.DinamicoViewHolder>() {

    class DinamicoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNombre: TextView = itemView.findViewById(R.id.tvNombre)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DinamicoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_nombre, parent, false)
        return DinamicoViewHolder(view)
    }

    override fun onBindViewHolder(holder: DinamicoViewHolder, position: Int) {
        val item = listaItems[position]
        holder.tvNombre.text = item

        holder.itemView.setOnClickListener {
            onItemClick(item)
        }
    }

    override fun getItemCount(): Int {
        return listaItems.size
    }
}
