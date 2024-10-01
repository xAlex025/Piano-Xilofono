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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
fun piano2() {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1E1E1E)) // Fondo gris oscuro
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Imagen decorativa en la parte superior
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(200.dp)
                .padding(16.dp)
                .background(Color(0xFF424242), shape = RoundedCornerShape(100.dp)) // Gris metálico oscuro
                .shadow(12.dp, shape = RoundedCornerShape(100.dp))
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "Logo",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(180.dp)
            )
        }

        // Teclas del piano
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            val sounds = listOf(
                R.raw.doo, R.raw.re, R.raw.mi,
                R.raw.fa, R.raw.sol, R.raw.la, R.raw.si
            )

            // Crear teclas mediante un bucle for
            for (sound in sounds) {
                PianoKey(context = context, soundResId = sound)
            }
        }
    }
}

@Composable
fun PianoKey(context: android.content.Context, soundResId: Int) {
    var isPressed by remember { mutableStateOf(false) }

    // Animación de cambio de color
    val keyColor by animateColorAsState(if (isPressed) Color(0xFF00B0FF) else Color(0xFFE0E0E0)) // Azul claro al presionar, blanco suave normalmente

    // Animación de sombra (elevación) que cambia cuando se presiona
    val shadowElevation by animateDpAsState(if (isPressed) 2.dp else 8.dp)

    // MediaPlayer para reproducir el sonido
    var mediaPlayer: MediaPlayer? = null

    Box(
        modifier = Modifier
            .width(80.dp)
            .height(160.dp)
            .background(keyColor, shape = RoundedCornerShape(16.dp))
            .clickable {
                isPressed = true // Cambiar color al presionar

                // Reproducir sonido
                mediaPlayer = MediaPlayer.create(context, soundResId)
                mediaPlayer?.start()

                // Liberar el MediaPlayer después de reproducir
                mediaPlayer?.setOnCompletionListener {
                    mediaPlayer?.release()
                    mediaPlayer = null
                    isPressed = false // Volver al color original al terminar
                }
            }
            .shadow(shadowElevation, RoundedCornerShape(16.dp))
            .padding(4.dp),
        contentAlignment = Alignment.Center
    ) {
        // Resplandor visual cuando está presionado (opcional)
        if (isPressed) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(Color.Transparent, Color(0xFF00B0FF)),
                            radius = 200f
                        ),
                        shape = RoundedCornerShape(16.dp)
                    )
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PianoTheme {
        Greeting("Android")
    }
}