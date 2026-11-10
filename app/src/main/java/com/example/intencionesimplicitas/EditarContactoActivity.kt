package com.example.intencionesimplicitas

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class EditarContactoActivity : AppCompatActivity() {
    private var posicionContacto: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_contacto)

        val etNombreContacto = findViewById<EditText>(R.id.etNombreContacto)
        val etTelefonoContacto = findViewById<EditText>(R.id.etTelefonoContacto)
        val btnGuardarContacto = findViewById<Button>(R.id.btnGuardarContacto)

        // recibir contacto si viene en modo edicion
        posicionContacto = intent.getIntExtra("posicion", -1)
        val contactoExistente = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra("contacto", Contacto::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getSerializableExtra("contacto") as? Contacto
        }

        if (contactoExistente != null) {
            etNombreContacto.setText(contactoExistente.nombre)
            etTelefonoContacto.setText(contactoExistente.telefono)
        }

        // guardar los cambios y retornar el contacto
        btnGuardarContacto.setOnClickListener {
            val nombre = etNombreContacto.text.toString().trim()
            val telefono = etTelefonoContacto.text.toString().trim()

            if (nombre.isNotEmpty() && telefono.isNotEmpty()) {
                val contactoActualizado = Contacto(nombre, telefono)
                val intentResultado = Intent()
                intentResultado.putExtra("contacto", contactoActualizado)
                intentResultado.putExtra("posicion", posicionContacto)
                setResult(RESULT_OK, intentResultado)
                finish()
            }
        }
    }
}
