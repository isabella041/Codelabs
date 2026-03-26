package com.example.medellinguide.model


// Representa una categoría de recomendaciones (ej: Restaurantes, Parques).
// El iconRes guarda el ícono de Material Symbols que se mostrará en el menú.
data class Category(
    val id: Int,
    val nameRes: Int,       // Nombre localizable desde strings.xml
    val iconRes: Int        // Ícono representativo de la categoría
)