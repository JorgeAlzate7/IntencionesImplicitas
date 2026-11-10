package com.example.intencionesimplicitas

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetalleLugarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_lugar)

        // referencias a los componentes visuales de la interfaz
        val imgDetalleLugar = findViewById<ImageView>(R.id.imgDetalleLugar)
        val tvNombreLugar = findViewById<TextView>(R.id.tvNombreLugar)
        val tvDescripcionLugar = findViewById<TextView>(R.id.tvDescripcionLugar)

        // recibir el objeto lugar enviado desde la actividad anterior
        val lugar = intent.getSerializableExtra("lugar") as? Lugar

        // asignar la informacion a cada vista si el objeto no es nulo
        if (lugar != null) {
            imgDetalleLugar.setImageResource(lugar.imagen)
            tvNombreLugar.text = lugar.nombre
            tvDescripcionLugar.text = lugar.descripcion
        }
    }
}
