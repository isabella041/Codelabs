package com.example.affirmations.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

/**
 * Esta es la clase de datos simple .
 * La uso para empaquetar la información que necesita cada tarjeta: 
 * un texto y una imagen.
 */
data class Affirmation(
    // Uso estas anotaciones para asegurarme de que solo me pasen IDs de recursos válidos
    @StringRes val stringResourceId: Int,
    @DrawableRes val imageResourceId: Int
)