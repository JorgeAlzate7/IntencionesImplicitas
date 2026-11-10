package com.example.intencionesimplicitas

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListaNotasActivity : AppCompatActivity() {
    private val listaNotas = mutableListOf(
        Nota("Reunion SENA", "Reunion de seguimiento del proyecto movil"),
        Nota("Lista de Compras", "Comprar cuadernos lapiceros y carpetas")
    )

    private lateinit var adapter: NotasAdapter

    // launcher para crear nueva nota
    private val nuevaNotaLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK && result.data != null) {
            val nuevaNota = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                result.data?.getSerializableExtra("nota", Nota::class.java)
            } else {
                @Suppress("DEPRECATION")
                result.data?.getSerializableExtra("nota") as? Nota
            }
            if (nuevaNota != null) {
                listaNotas.add(nuevaNota)
                adapter.notifyItemInserted(listaNotas.size - 1)
            }
        }
    }

    // launcher para ver y actualizar nota
    private val verNotaLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK && result.data != null) {
            val notaEditada = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                result.data?.getSerializableExtra("nota", Nota::class.java)
            } else {
                @Suppress("DEPRECATION")
                result.data?.getSerializableExtra("nota") as? Nota
            }
            val pos = result.data?.getIntExtra("posicion", -1) ?: -1
            if (notaEditada != null && pos >= 0 && pos < listaNotas.size) {
                listaNotas[pos] = notaEditada
                adapter.notifyItemChanged(pos)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_notas)

        val rvNotas = findViewById<RecyclerView>(R.id.rvNotas)
        rvNotas.layoutManager = LinearLayoutManager(this)

        adapter = NotasAdapter(listaNotas) { notaSeleccionada ->
            val pos = listaNotas.indexOf(notaSeleccionada)
            val intentVista = Intent(this, VistaNotaActivity::class.java)
            intentVista.putExtra("nota", notaSeleccionada)
            intentVista.putExtra("posicion", pos)
            verNotaLauncher.launch(intentVista)
        }
        rvNotas.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_principal_notas, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.opcionNuevaNota) {
            val intentNueva = Intent(this, EdicionNotaActivity::class.java)
            nuevaNotaLauncher.launch(intentNueva)
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
