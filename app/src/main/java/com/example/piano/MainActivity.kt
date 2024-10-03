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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.Color
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
    var Piano by remember { mutableStateOf(true) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1E1E1E))
            .padding(16.dp)
    ) {

        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 16.dp, top = 16.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Aplicacion",
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF00B0FF),
                style = TextStyle(
                    shadow = Shadow(
                        color = Color.Black.copy(alpha = 0.3f),
                        offset = Offset(4f, 4f),
                        blurRadius = 4f
                    )
                )
            )

            Text(
                text = "Piano",
                fontSize = 32.sp,
                fontWeight = FontWeight.Light,
                color = Color.White
            )
        }


        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(200.dp)
                .padding(16.dp)
                .background(Color(0xFF424242), shape = RoundedCornerShape(100.dp))
                .shadow(12.dp, shape = RoundedCornerShape(100.dp))
                .align(Alignment.TopCenter)
        ) {
            Image(
                painter = painterResource(id = R.drawable.img2),
                contentDescription = "Logo",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(180.dp)
            )
        }


        Button(
            onClick = { Piano = !Piano },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp)
        ) {
            Text(text = if (Piano) "Cambiar a Xilofono" else "Cambiar a Piano")
        }


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 250.dp, bottom = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                val sonidosPiano = listOf(R.raw.doo, R.raw.re, R.raw.mi, R.raw.fa, R.raw.sol, R.raw.la, R.raw.si)
                val sonidosXilofono = listOf(R.raw.xylophone_a3, R.raw.xylophone_b3, R.raw.xylophone_c3, R.raw.xylophone_d3, R.raw.xylophone_e3, R.raw.xylophone_f3, R.raw.xylophone_g3)


                val nombresNotasPiano = listOf("Do", "Re", "Mi", "Fa", "Sol", "La", "Si")
                val nombresNotasXilofono = listOf("A3", "B3", "C3", "D3", "E3", "F3", "G3")


                val sonidos = if (Piano) sonidosPiano else sonidosXilofono

                val nombresNotas = if (Piano) nombresNotasPiano else nombresNotasXilofono


                for (i in sonidos.indices) {
                    teclaPiano(contexto = context, idSonido = sonidos[i], Piano = Piano, nota = nombresNotas[i])
                }
            }
        }
    }
}
@Composable
fun teclaPiano(contexto: android.content.Context, idSonido: Int, Piano: Boolean,   nota: String) {
    var esPulsado by remember { mutableStateOf(false) }


    val colorTeclas by animateColorAsState(
        if (esPulsado) {
            if (Piano) Color(0xFF00B0FF) else Color(0xFF8BC34A)
        } else {
            Color(0xFFE0E0E0)
        }
    )


    val sombra by animateDpAsState(targetValue = if (esPulsado) 2.dp else 8.dp)


    LaunchedEffect(esPulsado) {
        if (esPulsado) {

            kotlinx.coroutines.delay(300L)
            esPulsado = false
        }
    }

    Box(
        modifier = Modifier
            .width(80.dp)
            .height(200.dp)
            .background(colorTeclas, shape = RoundedCornerShape(16.dp))
            .clickable(
                onClick = {
                    esPulsado = true


                    val mediaPlayer = MediaPlayer.create(contexto, idSonido)
                    mediaPlayer.start()

                    mediaPlayer.setOnCompletionListener {
                        mediaPlayer.release()

                    }
                }
            )
            .shadow(sombra, RoundedCornerShape(16.dp))
            .padding(4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = if (esPulsado) nota else "",
            color = Color.Black,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PianoTheme {
        Greeting("Android")
    }
}