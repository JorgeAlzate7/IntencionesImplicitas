package com.example.intencionesimplicitas

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// adaptador para mostrar la lista de productos con imagen nombre y evento click
class ProductosAdapter(
    private val listaProductos: List<Producto>,
    private val onItemClick: ((Producto) -> Unit)? = null
) : RecyclerView.Adapter<ProductosAdapter.ProductoViewHolder>() {

    class ProductoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgProducto: ImageView = itemView.findViewById(R.id.imgProducto)
        val tvNombreProducto: TextView = itemView.findViewById(R.id.tvNombreProducto)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.elemento_lista, parent, false)
        return ProductoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = listaProductos[position]
        holder.tvNombreProducto.text = "${producto.nombre} - $${producto.precio}"
        if (producto.imagen != 0) {
            holder.imgProducto.setImageResource(producto.imagen)
        } else {
            holder.imgProducto.setImageResource(R.drawable.ic_launcher_foreground)
        }

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(producto)
        }
    }

    override fun getItemCount(): Int {
        return listaProductos.size
    }
}
