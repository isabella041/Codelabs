package com.example.medellinguide.data

import com.example.medellinguide.R
import com.example.medellinguide.model.Category
import com.example.medellinguide.model.Recommendation

// DataSource actúa como la fuente de verdad de la app.
// En una app real esto podría conectarse a una API o base de datos local (Room).
// Por ahora los datos son estáticos, pero la arquitectura permite escalarlos fácilmente.
object DataSource {

    // Lista de categorías disponibles en la app
    val categories = listOf(
        Category(id = 1, nameRes = R.string.category_restaurants, iconRes = R.drawable.ic_restaurant),
        Category(id = 2, nameRes = R.string.category_parks,       iconRes = R.drawable.ic_park),
        Category(id = 3, nameRes = R.string.category_cafes,       iconRes = R.drawable.ic_cafe)
    )

    // Todos los lugares registrados. Están asociados a una categoría via categoryId.
    val recommendations = listOf(

        // --- Restaurantes ---
        Recommendation(
            id = 1,
            nameRes = R.string.rest_carmen_name,
            descriptionRes = R.string.rest_carmen_desc,
            imageRes = R.drawable.ic_launcher_background,
            categoryId = 1
        ),
        Recommendation(
            id = 2,
            nameRes = R.string.rest_bobe_name,
            descriptionRes = R.string.rest_bobe_desc,
            imageRes = R.drawable.ic_launcher_background,
            categoryId = 1
        ),
        Recommendation(
            id = 3,
            nameRes = R.string.rest_elcielo_name,
            descriptionRes = R.string.rest_elcielo_desc,
            imageRes = R.drawable.ic_launcher_background,
            categoryId = 1
        ),

        // --- Parques / Naturaleza ---
        Recommendation(
            id = 4,
            nameRes = R.string.park_arvi_name,
            descriptionRes = R.string.park_arvi_desc,
            imageRes = R.drawable.ic_launcher_background,
            categoryId = 2
        ),
        Recommendation(
            id = 5,
            nameRes = R.string.park_norte_name,
            descriptionRes = R.string.park_norte_desc,
            imageRes = R.drawable.ic_launcher_background,
            categoryId = 2
        ),
        Recommendation(
            id = 6,
            nameRes = R.string.park_botero_name,
            descriptionRes = R.string.park_botero_desc,
            imageRes = R.drawable.ic_launcher_background,
            categoryId = 2
        ),

        // --- Cafés / Vida nocturna ---
        Recommendation(
            id = 7,
            nameRes = R.string.cafe_pergamino_name,
            descriptionRes = R.string.cafe_pergamino_desc,
            imageRes = R.drawable.ic_launcher_background,
            categoryId = 3
        ),
        Recommendation(
            id = 8,
            nameRes = R.string.cafe_urbania_name,
            descriptionRes = R.string.cafe_urbania_desc,
            imageRes = R.drawable.ic_launcher_background,
            categoryId = 3
        ),
        Recommendation(
            id = 9,
            nameRes = R.string.cafe_envejecido_name,
            descriptionRes = R.string.cafe_envejecido_desc,
            imageRes = R.drawable.ic_launcher_background,
            categoryId = 3
        )
    )

    // Función auxiliar para filtrar lugares por categoría.
    // El ViewModel la usa en vez de exponer toda la lista cruda.
    fun getRecommendationsByCategory(categoryId: Int): List<Recommendation> {
        return recommendations.filter { it.categoryId == categoryId }
    }
}