package com.example.intencionesimplicitas

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class EdicionNotaActivity : AppCompatActivity() {
    private var posicionNota: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edicion_nota)

        val etTituloNota = findViewById<EditText>(R.id.etTituloNota)
        val etContenidoNota = findViewById<EditText>(R.id.etContenidoNota)
        val btnGuardarNota = findViewById<Button>(R.id.btnGuardarNota)

        posicionNota = intent.getIntExtra("posicion", -1)
        val notaExistente = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra("nota", Nota::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getSerializableExtra("nota") as? Nota
        }

        if (notaExistente != null) {
            etTituloNota.setText(notaExistente.titulo)
            etContenidoNota.setText(notaExistente.contenido)
        }

        btnGuardarNota.setOnClickListener {
            val titulo = etTituloNota.text.toString().trim()
            val contenido = etContenidoNota.text.toString().trim()

            if (titulo.isNotEmpty()) {
                val notaEditada = Nota(titulo, contenido)
                val intentResult = Intent()
                intentResult.putExtra("nota", notaEditada)
                intentResult.putExtra("posicion", posicionNota)
                setResult(RESULT_OK, intentResult)
                finish()
            }
        }
    }
}
