package com.example.piano

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.piano.ui.theme.PianoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PianoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    piano2()
}


@Composable
fun piano2(){

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black) // Fondo negro
            .padding(16.dp), // Margen general
        horizontalAlignment = Alignment.CenterHorizontally, // Alineación horizontal al centro
        verticalArrangement = Arrangement.SpaceBetween // Espacio distribuido entre elementos
    ) {
        // Imagen en la parte superior
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background), // Reemplaza con tu recurso de imagen
            contentDescription = "Logo",
            modifier = Modifier
                .size(200.dp) // Tamaño de la imagen
                .padding(top = 16.dp), // Margen superior
            contentScale = ContentScale.Crop // Escalado para la imagen
        )

        // Teclas del piano en la parte inferior
        Row(
            modifier = Modifier
                .fillMaxWidth() // Ocupar todo el ancho
                .padding(16.dp), // Separación entre las teclas y los bordes
            horizontalArrangement = Arrangement.SpaceEvenly // Distribuir las teclas de manera uniforme
        ) {
            // Crear 7 teclas
            for (i in 1..7) {
                PianoKey()
            }
        }
    }
}
/*
@Composable
fun PianoKey(label: String, onClick: () -> Unit) {
    // Cada caja (tecla) será un cuadrado con fondo y texto
    // Cada tecla será una caja rectangular alargada
    Box(
        modifier = Modifier
            .width(80.dp) // Ancho de las teclas
            .height(160.dp) // Altura mediana
            .background(color = Color.White, shape = RoundedCornerShape(16.dp)) // Esquinas redondeadas
            .clickable { onClick() } // Acción clic
            .padding(4.dp), // Pequeña separación interna
        contentAlignment = Alignment.Center // Centrar el texto dentro de la tecla
    ) {

    }
}
*/
@Composable
fun PianoKey( ) {
    // Estado para rastrear si la tecla está presionada o no
    var isPressed by remember { mutableStateOf(false) }

    // Cambiar color según si está presionado
    val keyColor = if (isPressed) Color.Yellow else Color.White

    // Cada tecla será una caja rectangular redondeada
    Box(
        modifier = Modifier
            .width(80.dp) // Ancho de las teclas
            .height(160.dp) // Altura mediana
            .background(color = keyColor, shape = RoundedCornerShape(16.dp)) // Cambiar color con esquinas redondeada
            .padding(4.dp), // Pequeña separación interna
        contentAlignment = Alignment.Center // Centrar el texto dentro de la tecla
    ) {

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PianoTheme {
        Greeting("Android")
    }
}