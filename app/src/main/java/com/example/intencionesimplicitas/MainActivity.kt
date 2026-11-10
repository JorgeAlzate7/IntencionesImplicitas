package com.example.intencionesimplicitas

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    // launcher para recibir el producto seleccionado del ejercicio 5
    private val productoLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK && result.data != null) {
            val producto = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                result.data?.getSerializableExtra("producto_seleccionado", Producto::class.java)
            } else {
                @Suppress("DEPRECATION")
                result.data?.getSerializableExtra("producto_seleccionado") as? Producto
            }
            if (producto != null) {
                val tvProducto = findViewById<TextView>(R.id.tvProductoSeleccionado)
                tvProducto.text = producto.nombre
            }
        }
    }

    // launcher para seleccionar contacto del telefono del ejercicio 10
    private val seleccionarContactoLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK && result.data != null) {
            val contactUri: Uri? = result.data?.data
            if (contactUri != null) {
                val cursor = contentResolver.query(contactUri, null, null, null, null)
                cursor?.use {
                    if (it.moveToFirst()) {
                        val nameIndex = it.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
                        if (nameIndex >= 0) {
                            val nombreContacto = it.getString(nameIndex)
                            val tvContacto = findViewById<TextView>(R.id.tvContactoSeleccionadoNombre)
                            tvContacto.text = nombreContacto
                        }
                    }
                }
            }
        }
    }

    // launcher para abrir archivo pdf del ejercicio 11
    private val abrirPdfLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK && result.data != null) {
            val pdfUri: Uri? = result.data?.data
            if (pdfUri != null) {
                val intentViewPdf = Intent(Intent.ACTION_VIEW).apply {
                    setDataAndType(pdfUri, "application/pdf")
                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                }
                try {
                    startActivity(intentViewPdf)
                } catch (_: Exception) {
                    Toast.makeText(this, "No hay aplicacion para abrir el PDF", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // ejercicios 1 2 y 3 configuracion del recyclerview de lugares
        val rvLugares = findViewById<RecyclerView>(R.id.rvLugares)
        rvLugares.layoutManager = LinearLayoutManager(this)

        val listaLugares = listOf(
            Lugar("Parque Simon Bolivar", "El parque mas grande de la ciudad", R.drawable.ic_map),
            Lugar("Museo del Oro", "Coleccion precolombina unica", R.drawable.ic_web),
            Lugar("Monserrate", "Santuario en la cima de la montana", R.drawable.ic_laptop)
        )

        val lugaresAdapter = LugaresAdapter(listaLugares) { lugarSeleccionado ->
            val intentDetalle = Intent(this, DetalleLugarActivity::class.java)
            intentDetalle.putExtra("lugar", lugarSeleccionado)
            startActivity(intentDetalle)
        }
        rvLugares.adapter = lugaresAdapter

        // ejercicio 4 agregar elementos dinamicamente y toast al hacer click
        val etNuevoItem = findViewById<EditText>(R.id.etNuevoItem)
        val btnAgregarItem = findViewById<Button>(R.id.btnAgregarItem)
        val rvDinamico = findViewById<RecyclerView>(R.id.rvDinamico)

        val listaDinamica = mutableListOf("Elemento inicial 1", "Elemento inicial 2")
        rvDinamico.layoutManager = LinearLayoutManager(this)

        val dinamicoAdapter = DinamicoAdapter(listaDinamica) { itemSeleccionado ->
            Toast.makeText(this, itemSeleccionado, Toast.LENGTH_SHORT).show()
        }
        rvDinamico.adapter = dinamicoAdapter

        btnAgregarItem.setOnClickListener {
            val texto = etNuevoItem.text.toString().trim()
            if (texto.isNotEmpty()) {
                listaDinamica.add(texto)
                dinamicoAdapter.notifyItemInserted(listaDinamica.size - 1)
                etNuevoItem.text.clear()
            }
        }

        // ejercicio 5 abrir lista de productos y recibir seleccion
        val btnAbrirLista = findViewById<Button>(R.id.btnAbrirListaProductos)
        btnAbrirLista.setOnClickListener {
            val intentLista = Intent(this, ListaActivity::class.java)
            productoLauncher.launch(intentLista)
        }

        // ejercicio 6 abrir gestion de contactos con menus
        val btnContactos = findViewById<Button>(R.id.btnGestionContactos)
        btnContactos.setOnClickListener {
            startActivity(Intent(this, ListaContactosActivity::class.java))
        }

        // ejercicio 7 abrir gestion de notas con menus e intencion compartir
        val btnNotas = findViewById<Button>(R.id.btnGestionNotas)
        btnNotas.setOnClickListener {
            startActivity(Intent(this, ListaNotasActivity::class.java))
        }

        // ejercicio 8 mostrar ubicacion en google maps
        val btnUbicacion = findViewById<Button>(R.id.btnMostrarUbicacion)
        btnUbicacion.setOnClickListener {
            val gmmIntentUri = Uri.parse("geo:4.6097,-74.0817?q=SENA+Bogota")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            startActivity(mapIntent)
        }

        // ejercicio 9 compartir texto con intencion implicita
        val etTextoCompartir = findViewById<EditText>(R.id.etTextoCompartir)
        val btnCompartir = findViewById<Button>(R.id.btnCompartirTexto)
        btnCompartir.setOnClickListener {
            val texto = etTextoCompartir.text.toString().trim()
            if (texto.isNotEmpty()) {
                val sendIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, texto)
                }
                startActivity(Intent.createChooser(sendIntent, "Compartir texto"))
            } else {
                Toast.makeText(this, "Escribe un texto para compartir", Toast.LENGTH_SHORT).show()
            }
        }

        // ejercicio 10 seleccionar contacto del telefono
        val btnSeleccionarContacto = findViewById<Button>(R.id.btnSeleccionarContacto)
        btnSeleccionarContacto.setOnClickListener {
            val intentPick = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
            seleccionarContactoLauncher.launch(intentPick)
        }

        // ejercicio 11 abrir y visualizar archivo pdf
        val btnAbrirPdf = findViewById<Button>(R.id.btnAbrirPdf)
        btnAbrirPdf.setOnClickListener {
            val intentPdf = Intent(Intent.ACTION_GET_CONTENT).apply {
                type = "application/pdf"
                addCategory(Intent.CATEGORY_OPENABLE)
            }
            abrirPdfLauncher.launch(intentPdf)
        }

        // ejercicio 12 intenciones implicitas combinadas con iconos
        val btnLlamar = findViewById<Button>(R.id.btnLlamar)
        val btnCorreo = findViewById<Button>(R.id.btnCorreo)
        val btnMapa = findViewById<Button>(R.id.btnMapa)
        val btnWeb = findViewById<Button>(R.id.btnWeb)

        btnLlamar.setOnClickListener {
            val intentDial = Intent(Intent.ACTION_DIAL, Uri.parse("tel:3001234567"))
            startActivity(intentDial)
        }

        btnCorreo.setOnClickListener {
            val intentEmail = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:contacto@sena.edu.co")
                putExtra(Intent.EXTRA_SUBJECT, "Consulta Guia SENA")
            }
            startActivity(intentEmail)
        }

        btnMapa.setOnClickListener {
            val mapUri = Uri.parse("geo:4.6097,-74.0817?q=SENA")
            startActivity(Intent(Intent.ACTION_VIEW, mapUri))
        }

        btnWeb.setOnClickListener {
            val webUri = Uri.parse("https://www.sena.edu.co")
            startActivity(Intent(Intent.ACTION_VIEW, webUri))
        }
    }
}
