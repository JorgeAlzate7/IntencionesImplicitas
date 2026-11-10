package com.example.intencionesimplicitas

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class DetalleContactoActivity : AppCompatActivity() {
    private var contacto: Contacto? = null
    private var posicion: Int = -1

    private lateinit var tvNombreContactoDetalle: TextView
    private lateinit var tvTelefonoContactoDetalle: TextView

    // launcher para recibir el resultado de la edicion del contacto
    private val editarLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK && result.data != null) {
            val contactoEditado = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                result.data?.getSerializableExtra("contacto", Contacto::class.java)
            } else {
                @Suppress("DEPRECATION")
                result.data?.getSerializableExtra("contacto") as? Contacto
            }
            if (contactoEditado != null) {
                contacto = contactoEditado
                tvNombreContactoDetalle.text = contactoEditado.nombre
                tvTelefonoContactoDetalle.text = contactoEditado.telefono

                // enviar el resultado actualizado a la actividad principal
                val resultIntent = Intent()
                resultIntent.putExtra("contacto", contactoEditado)
                resultIntent.putExtra("posicion", posicion)
                setResult(RESULT_OK, resultIntent)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_contacto)

        tvNombreContactoDetalle = findViewById(R.id.tvNombreContactoDetalle)
        tvTelefonoContactoDetalle = findViewById(R.id.tvTelefonoContactoDetalle)

        posicion = intent.getIntExtra("posicion", -1)
        contacto = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra("contacto", Contacto::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getSerializableExtra("contacto") as? Contacto
        }

        contacto?.let {
            tvNombreContactoDetalle.text = it.nombre
            tvTelefonoContactoDetalle.text = it.telefono
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detalle_contacto, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.opcionEditarContacto -> {
                val intentEditar = Intent(this, EditarContactoActivity::class.java)
                intentEditar.putExtra("contacto", contacto)
                intentEditar.putExtra("posicion", posicion)
                editarLauncher.launch(intentEditar)
                true
            }
            R.id.opcionEliminarContacto -> {
                val intentEliminar = Intent()
                intentEliminar.putExtra("posicion", posicion)
                intentEliminar.putExtra("eliminar", true)
                setResult(RESULT_OK, intentEliminar)
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
