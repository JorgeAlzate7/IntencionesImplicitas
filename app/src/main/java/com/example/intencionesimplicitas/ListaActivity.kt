package com.example.intencionesimplicitas

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista)

        // lista de productos para el ejercicio 5
        val listaProductos = listOf(
            Producto("Televisor Smart TV", precio = 1200000.0),
            Producto("Computador Portatil", precio = 2500000.0),
            Producto("Celular Galaxy", precio = 950000.0)
        )

        val rvListaProductos = findViewById<RecyclerView>(R.id.rvListaProductos)
        rvListaProductos.layoutManager = LinearLayoutManager(this)

        // al seleccionar un producto se devuelve a mainactivity con result ok
        val adapter = ProductosAdapter(listaProductos) { productoSeleccionado ->
            val resultIntent = Intent()
            resultIntent.putExtra("producto_seleccionado", productoSeleccionado)
            setResult(RESULT_OK, resultIntent)
            finish()
        }
        rvListaProductos.adapter = adapter
    }
}
