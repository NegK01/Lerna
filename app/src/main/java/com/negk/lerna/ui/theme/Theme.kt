package com.negk.lerna.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// 🎨 Paleta clara
private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF0D1B2A),   // Azul marino oscuro
    secondary = Color(0xFF1B263B), // Azul marino distinto
    tertiary = Color(0xFF415A77),  // Azul complementario
    background = Color(0xFFFFFFFF), // Blanco
    surface = Color(0xFFFFFFFF),    // Blanco
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF000000), // Texto negro
    onSurface = Color(0xFF000000)     // Texto negro
)

// 🎨 Paleta oscura
private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF1B263B),   // Azul marino intermedio → para cards y botones destacados
    secondary = Color(0xFF415A77), // Azul un poco más claro → para acentos y estados secundarios
    tertiary = Color(0xFF778DA9),  // Azul grisáceo → decorativo, iconos de soporte
    background = Color(0xFF0D1B2A), // Azul marino muy oscuro → fondo de la pantalla
    surface = Color(0xFF1B263B),   // Igual al primary, pero dedicado a cards/dialogs
    surfaceBright = Color(0xFFE0E8F2),   // Igual al primary, pero dedicado a cards/dialogs
    onPrimary = Color.White,       // Texto sobre primary
    onSecondary = Color.White,     // Texto sobre secondary
    onTertiary = Color.White,      // Texto sobre tertiary
    onBackground = Color(0xFFE0E0E0), // Texto sobre fondo (gris claro)
    onSurface = Color.White        // Texto sobre cards/surfaces
)


@Composable
fun LernaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme1 = if (darkTheme) DarkColorScheme else LightColorScheme
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val AppTypography = Typography(
        titleLarge = TextStyle(
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
            lineHeight = 24.sp
        ),
        titleMedium = TextStyle(
            fontSize = 14.sp,
            lineHeight = 20.sp
        ),
        bodyMedium = TextStyle(
            fontSize = 15.sp,
            lineHeight = 20.sp,
            color = if (darkTheme) Color(0xFFE0E0E0) else Color(0xFF666666) // Texto gris secundario
        )
    )

    MaterialTheme(
        colorScheme = colorScheme1,
        typography = AppTypography,
        content = content
    )
}
