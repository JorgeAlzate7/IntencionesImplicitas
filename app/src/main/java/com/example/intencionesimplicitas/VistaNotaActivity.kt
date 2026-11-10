package com.example.intencionesimplicitas

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class VistaNotaActivity : AppCompatActivity() {
    private var nota: Nota? = null
    private var posicion: Int = -1

    private lateinit var tvTituloNotaVista: TextView
    private lateinit var tvContenidoNotaVista: TextView

    // launcher para editar la nota
    private val editarNotaLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK && result.data != null) {
            val notaEditada = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                result.data?.getSerializableExtra("nota", Nota::class.java)
            } else {
                @Suppress("DEPRECATION")
                result.data?.getSerializableExtra("nota") as? Nota
            }
            if (notaEditada != null) {
                nota = notaEditada
                tvTituloNotaVista.text = notaEditada.titulo
                tvContenidoNotaVista.text = notaEditada.contenido

                val resultIntent = Intent()
                resultIntent.putExtra("nota", notaEditada)
                resultIntent.putExtra("posicion", posicion)
                setResult(RESULT_OK, resultIntent)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vista_nota)

        tvTituloNotaVista = findViewById(R.id.tvTituloNotaVista)
        tvContenidoNotaVista = findViewById(R.id.tvContenidoNotaVista)

        posicion = intent.getIntExtra("posicion", -1)
        nota = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra("nota", Nota::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getSerializableExtra("nota") as? Nota
        }

        nota?.let {
            tvTituloNotaVista.text = it.titulo
            tvContenidoNotaVista.text = it.contenido
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_vista_nota, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.opcionEditarNota -> {
                val intentEditar = Intent(this, EdicionNotaActivity::class.java)
                intentEditar.putExtra("nota", nota)
                intentEditar.putExtra("posicion", posicion)
                editarNotaLauncher.launch(intentEditar)
                true
            }
            R.id.opcionCompartirNota -> {
                // intencion implicita para compartir el contenido de la nota
                nota?.let { n ->
                    val intentCompartir = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_SUBJECT, n.titulo)
                        putExtra(Intent.EXTRA_TEXT, "${n.titulo}\n\n${n.contenido}")
                    }
                    startActivity(Intent.createChooser(intentCompartir, "Compartir nota"))
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
