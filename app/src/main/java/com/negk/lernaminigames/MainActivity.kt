package com.negk.lernaminigames

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.negk.lernaminigames.ui.theme.LernaTheme        // <-- Importa el tema visual (colores, tipografía, etc.) desde ui/theme
import com.negk.lernaminigames.core.navigation.AppNavHost // <-- Importa (AppNavHost.kt), nuestro archivo de navegacion principal

class MainActivity : ComponentActivity() {
    // Este metodo se ejecuta cuando Android crea la pantalla (Activity)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // LernaTheme viene de otro archivo (ui/theme)
            // Envuelve toda la UI para que use el mismo estilo (Material 3)
            LernaTheme {
                // AppNavHost viene de otro archivo (core/navigation)
                // Es el "router": decide qué pantallas se ven y maneja el NavController
                AppNavHost()
            }
        }
    }
}
