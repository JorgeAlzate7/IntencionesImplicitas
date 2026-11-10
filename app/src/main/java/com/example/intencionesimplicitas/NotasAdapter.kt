package com.example.intencionesimplicitas

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// adaptador para mostrar la lista de notas
class NotasAdapter(
    private val listaNotas: List<Nota>,
    private val onItemClick: (Nota) -> Unit
) : RecyclerView.Adapter<NotasAdapter.NotaViewHolder>() {

    class NotaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitulo: TextView = itemView.findViewById(R.id.tvNombreProducto)
        val tvContenido: TextView = itemView.findViewById(R.id.tvDescripcionLugar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_contacto, parent, false)
        return NotaViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotaViewHolder, position: Int) {
        val nota = listaNotas[position]
        holder.tvTitulo.text = nota.titulo
        holder.tvContenido.text = nota.contenido

        holder.itemView.setOnClickListener {
            onItemClick(nota)
        }
    }

    override fun getItemCount(): Int {
        return listaNotas.size
    }
}
