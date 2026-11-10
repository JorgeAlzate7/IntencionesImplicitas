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

class ListaContactosActivity : AppCompatActivity() {
    private val listaContactos = mutableListOf(
        Contacto("Juan Perez", "3001234567"),
        Contacto("Ana Martinez", "3109876543")
    )

    private lateinit var adapter: ContactosAdapter

    // launcher para anadir nuevo contacto
    private val nuevoContactoLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK && result.data != null) {
            val nuevoContacto = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                result.data?.getSerializableExtra("contacto", Contacto::class.java)
            } else {
                @Suppress("DEPRECATION")
                result.data?.getSerializableExtra("contacto") as? Contacto
            }
            if (nuevoContacto != null) {
                listaContactos.add(nuevoContacto)
                adapter.notifyItemInserted(listaContactos.size - 1)
            }
        }
    }

    // launcher para ver detalle o editar o eliminar contacto
    private val detalleContactoLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK && result.data != null) {
            val pos = result.data?.getIntExtra("posicion", -1) ?: -1
            val eliminar = result.data?.getBooleanExtra("eliminar", false) ?: false

            if (pos >= 0 && pos < listaContactos.size) {
                if (eliminar) {
                    listaContactos.removeAt(pos)
                    adapter.notifyItemRemoved(pos)
                } else {
                    val contactoEditado = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        result.data?.getSerializableExtra("contacto", Contacto::class.java)
                    } else {
                        @Suppress("DEPRECATION")
                        result.data?.getSerializableExtra("contacto") as? Contacto
                    }
                    if (contactoEditado != null) {
                        listaContactos[pos] = contactoEditado
                        adapter.notifyItemChanged(pos)
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_contactos)

        val rvContactos = findViewById<RecyclerView>(R.id.rvContactos)
        rvContactos.layoutManager = LinearLayoutManager(this)

        adapter = ContactosAdapter(listaContactos) { contactoSeleccionado ->
            val pos = listaContactos.indexOf(contactoSeleccionado)
            val intentDetalle = Intent(this, DetalleContactoActivity::class.java)
            intentDetalle.putExtra("contacto", contactoSeleccionado)
            intentDetalle.putExtra("posicion", pos)
            detalleContactoLauncher.launch(intentDetalle)
        }
        rvContactos.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_principal_contactos, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.opcionNuevoContacto) {
            val intentNuevo = Intent(this, EditarContactoActivity::class.java)
            nuevoContactoLauncher.launch(intentNuevo)
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
