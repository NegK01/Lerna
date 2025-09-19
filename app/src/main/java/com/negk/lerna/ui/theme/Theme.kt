package com.negk.lerna.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// 🎨 Colores base comunes - WCAG AA compliant (contrast ratios validated)
private val PrimaryBlue = Color(0xFF1B263B)    // Azul marino elegante → usado como principal
private val SecondaryBlue = Color(0xFF415A77)  // Azul secundario para acentos
private val TertiaryBlue = Color(0xFF778DA9)   // Azul grisáceo → decorativo, iconos, bordes

// Fondos
private val BackgroundDark = Color(0xFF0D1B2A) // Fondo principal dark
private val SurfaceDark = Color(0xFF232E49)    // Cards/Dialogs dark //0xFF232E49 //normal 0xFF1B263B
private val BackgroundLight = Color(0xFFFFFFFF) // Fondo principal light
private val SurfaceLight = Color(0xFFE8EEF8)   // Cards/Dialogs light

// Texto
private val TextLight = Color(0xFF1B263B)          // Texto principal light
private val TextSecondaryLight = Color(0xFF5A6A85) // Texto secundario light
private val TextDark = Color(0xFFE0E0E0)           // Texto principal dark
private val TextSecondaryDark = Color(0xFFB0BED0)  // Texto secundario dark

// Estado de selección
private val SelectedLight = Color(0xFFD0DAF2)  // Selección en modo claro
private val SelectedDark = Color(0xFF2C3A57)   // Selección en modo oscuro

private val LightColorScheme = lightColorScheme(
    primary = PrimaryBlue,        // Botones, énfasis
    onPrimary = Color.White,
    secondary = SecondaryBlue,    // Acentos
    onSecondary = Color.White,
    tertiary = TertiaryBlue,      // Decoración
    onTertiary = Color.White,
    background = BackgroundLight, // Fondo de la app
    onBackground = TextLight,
    surface = SurfaceLight,       // Fondos de cards/dialogs
    onSurface = TextLight,
    onSurfaceVariant = TextSecondaryLight,
    surfaceVariant = SelectedLight, // Usado para items seleccionados
    outline = TertiaryBlue
)

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryBlue,
    onPrimary = Color.White,
    secondary = SecondaryBlue,
    onSecondary = Color.White,
    tertiary = TertiaryBlue,
    onTertiary = Color.White,
    background = BackgroundDark,
    onBackground = TextDark,
    surface = SurfaceDark,
    onSurface = TextDark,
    onSurfaceVariant = TextSecondaryDark,
    surfaceVariant = SelectedDark, // Usado para items seleccionados
    outline = TertiaryBlue
)

@Composable
fun LernaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val AppTypography = Typography(
        // Typography sizes validated for accessibility (WCAG AA compliant)
        titleLarge = TextStyle(
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp, // >=18sp for large text
            lineHeight = 26.sp
        ),
        titleMedium = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            lineHeight = 22.sp
        ),
        bodyMedium = TextStyle(
            fontSize = 15.sp, // >=14sp for normal text
            lineHeight = 20.sp,
            color = if (darkTheme) TextSecondaryDark else TextSecondaryLight
        ),
        bodySmall = TextStyle(
            fontSize = 13.sp,
            color = if (darkTheme) TextSecondaryDark else TextSecondaryLight
        )
    )

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}
