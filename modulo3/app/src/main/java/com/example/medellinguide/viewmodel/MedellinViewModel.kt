package com.example.medellinguide.viewmodel

import androidx.lifecycle.ViewModel
import com.example.medellinguide.data.DataSource
import com.example.medellinguide.model.Category
import com.example.medellinguide.model.Recommendation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

// UiState centraliza todo lo que la interfaz necesita saber.
// Tener un solo objeto de estado evita inconsistencias entre variables separadas.
data class MedellinUiState(
    val categories: List<Category> = DataSource.categories,
    val selectedCategory: Category? = null,
    val recommendationsForCategory: List<Recommendation> = emptyList(),
    val selectedRecommendation: Recommendation? = null,
    val isShowingDetail: Boolean = false
)

class MedellinViewModel : ViewModel() {

    // _uiState es privado y mutable → solo el ViewModel puede modificarlo.
    // uiState es público e inmutable → la UI solo puede leerlo.
    // Este patrón es la base del flujo unidireccional de datos (UDF).
    private val _uiState = MutableStateFlow(MedellinUiState())
    val uiState: StateFlow<MedellinUiState> = _uiState.asStateFlow()

    // El usuario seleccionó una categoría en el HomeScreen.
    // Actualizamos el estado con la categoría elegida y cargamos sus lugares.
    fun onCategorySelected(category: Category) {
        _uiState.update { currentState ->
            currentState.copy(
                selectedCategory = category,
                recommendationsForCategory = DataSource.getRecommendationsByCategory(category.id),
                selectedRecommendation = null,
                isShowingDetail = false
            )
        }
    }

    // El usuario tocó un lugar de la lista → mostrar su detalle.
    fun onRecommendationSelected(recommendation: Recommendation) {
        _uiState.update { currentState ->
            currentState.copy(
                selectedRecommendation = recommendation,
                isShowingDetail = true
            )
        }
    }

    // El usuario presionó "atrás" en el detalle → volver a la lista.
    fun onBackFromDetail() {
        _uiState.update { currentState ->
            currentState.copy(
                selectedRecommendation = null,
                isShowingDetail = false
            )
        }
    }

    // Regresa al home limpiando la categoría seleccionada.
    fun onBackToHome() {
        _uiState.update { currentState ->
            currentState.copy(
                selectedCategory = null,
                recommendationsForCategory = emptyList(),
                selectedRecommendation = null,
                isShowingDetail = false
            )
        }
    }
}