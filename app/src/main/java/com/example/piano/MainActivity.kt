package com.example.piano

import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.piano.ui.theme.PianoTheme
import androidx.compose.ui.text.TextStyle

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
fun piano2() {
    val context = LocalContext.current
    var isPiano by remember { mutableStateOf(true) } // Estado para rastrear si estamos en piano o guitarra

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1E1E1E)) // Fondo gris oscuro
            .padding(16.dp)
    ) {
        // Título a la izquierda en la parte superior
        Column(
            modifier = Modifier
                .align(Alignment.TopStart) // Alinear a la parte superior izquierda
                .padding(start = 16.dp, top = 16.dp), // Espacio adicional a la izquierda y en la parte superior
            verticalArrangement = Arrangement.Top // Alinear verticalmente hacia arriba
        ) {
            Text(
                text = "Piano",
                fontSize = 48.sp, // Tamaño de la fuente grande
                fontWeight = FontWeight.Bold, // Peso de la fuente
                color = Color(0xFF00B0FF), // Color azul brillante
                style = TextStyle(
                    shadow = Shadow(
                        color = Color.Black.copy(alpha = 0.3f),
                        offset = Offset(4f, 4f),
                        blurRadius = 4f
                    )
                )
            )

            Text(
                text = "App",
                fontSize = 32.sp, // Tamaño de la fuente más pequeño
                fontWeight = FontWeight.Light, // Peso de la fuente más ligero
                color = Color.White // Color del texto
            )
        }

        // Imagen decorativa en la parte superior
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(200.dp)
                .padding(16.dp)
                .background(Color(0xFF424242), shape = RoundedCornerShape(100.dp)) // Gris metálico oscuro
                .shadow(12.dp, shape = RoundedCornerShape(100.dp))
                .align(Alignment.TopCenter) // Centra la imagen en la parte superior
        ) {
            Image(
                painter = painterResource(id = R.drawable.img1),
                contentDescription = "Logo",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(180.dp)
            )
        }

        // Botón para alternar entre piano y guitarra en la parte superior derecha
        Button(
            onClick = { isPiano = !isPiano },
            modifier = Modifier
                .align(Alignment.TopEnd) // Mover el botón a la parte superior derecha
                .padding(8.dp)
        ) {
            Text(text = if (isPiano) "Cambiar a Guitarra" else "Cambiar a Piano")
        }

        // Teclas del piano o cuerdas de guitarra, en la parte inferior
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 250.dp, bottom = 16.dp), // Da más espacio vertical para las teclas
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom // Coloca las teclas en la parte inferior
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Sonidos para piano y guitarra
                val pianoSounds = listOf(R.raw.doo, R.raw.re, R.raw.mi, R.raw.fa, R.raw.sol, R.raw.la, R.raw.si)
                val guitarSounds = listOf(R.raw.doo, R.raw.re, R.raw.mi, R.raw.fa, R.raw.sol, R.raw.la, R.raw.si)

                // Seleccionar sonidos según el estado
                val sounds = if (isPiano) pianoSounds else guitarSounds

                // Crear teclas mediante un bucle for
                for (sound in sounds) {
                    PianoKey(context = context, soundResId = sound, isPiano = isPiano)
                }
            }
        }
    }
}

@Composable
fun PianoKey(context: android.content.Context, soundResId: Int, isPiano: Boolean) {
    var isPressed by remember { mutableStateOf(false) }

    // Animación de cambio de color dependiendo de si es piano o guitarra
    val keyColor by animateColorAsState(if (isPressed) {
        if (isPiano) Color(0xFF00B0FF) else Color(0xFF8BC34A) // Azul claro para piano, verde claro para guitarra
    } else {
        Color(0xFFE0E0E0) // Blanco suave normalmente
    })

    // Animación de sombra (elevación) que cambia cuando se presiona
    val shadowElevation by animateDpAsState(if (isPressed) 2.dp else 8.dp)

    // MediaPlayer para reproducir el sonido
    var mediaPlayer: MediaPlayer? = null

    Box(
        modifier = Modifier
            .width(80.dp)
            .height(200.dp) // Hacemos las teclas más largas
            .background(keyColor, shape = RoundedCornerShape(16.dp))
            .clickable(
                onClick = {
                    // Cambiar el estado de la tecla al presionar
                    isPressed = true

                    // Reproducir sonido
                    mediaPlayer = MediaPlayer.create(context, soundResId)
                    mediaPlayer?.start()

                    // Restablecer el estado cuando termine la reproducción del sonido
                    mediaPlayer?.setOnCompletionListener {
                        it.release() // Liberar el recurso de MediaPlayer
                        mediaPlayer = null
                        isPressed = false // Asegurar que el estado se restablezca al finalizar el sonido
                    }

                }
            )
            .shadow(shadowElevation, RoundedCornerShape(16.dp))
            .padding(4.dp),
        contentAlignment = Alignment.Center
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