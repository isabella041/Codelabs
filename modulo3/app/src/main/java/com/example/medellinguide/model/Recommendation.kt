package com.example.medellinguide.model

// Representa un lugar o sitio recomendado dentro de una categoría.
// Usamos una data class porque solo necesitamos almacenar y comparar datos,
// sin lógica de negocio asociada.
data class Recommendation(
    val id: Int,
    val nameRes: Int,           // Referencia al string en strings.xml
    val descriptionRes: Int,    // Descripción larga del lugar
    val imageRes: Int,          // Drawable del lugar
    val categoryId: Int         // A qué categoría pertenece
)