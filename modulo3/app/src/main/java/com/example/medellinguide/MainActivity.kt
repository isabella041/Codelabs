package com.example.medellinguide

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import com.example.medellinguide.ui.MedellinApp
import com.example.medellinguide.ui.theme.MedellinGuideTheme

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Contenido llega a los bordes de la pantalla (edge-to-edge)

        setContent {
            MedellinGuideTheme {
                // calculateWindowSizeClass mide el tamaño real de la ventana.
                // Esto es lo que nos permite saber si estamos en móvil o tablet.
                val windowSize = calculateWindowSizeClass(this)
                MedellinApp(windowSize = windowSize.widthSizeClass)
            }
        }
    }
}