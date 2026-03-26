package com.example.medellinguide.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.medellinguide.model.Recommendation
import com.example.medellinguide.viewmodel.MedellinUiState

// Layout de dos paneles para pantallas expandidas (tablets, foldables).
// El panel izquierdo muestra las categorías siempre visible,
// el panel derecho muestra la lista o el detalle según el estado.
@Composable
fun MedellinAdaptiveLayout(
    uiState: MedellinUiState,
    onCategorySelected: (com.example.medellinguide.model.Category) -> Unit,
    onRecommendationSelected: (Recommendation) -> Unit,
    onBackFromDetail: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.fillMaxSize()) {

        // Panel izquierdo: lista de categorías (peso 1 = 33% del ancho)
        NavigationRail(
            modifier = Modifier.fillMaxHeight()
        ) {
            uiState.categories.forEach { category ->
                NavigationRailItem(
                    selected = uiState.selectedCategory?.id == category.id,
                    onClick = { onCategorySelected(category) },
                    icon = {
                        Icon(
                            painter = androidx.compose.ui.res.painterResource(category.iconRes),
                            contentDescription = null
                        )
                    },
                    label = {
                        Text(androidx.compose.ui.res.stringResource(category.nameRes))
                    }
                )
            }
        }

        HorizontalDivider(modifier = Modifier.fillMaxHeight().width(1.dp))

        // Panel derecho: lista de recomendaciones o detalle (peso 2 = 67% del ancho)
        Box(modifier = Modifier.weight(1f)) {
            when {
                uiState.isShowingDetail && uiState.selectedRecommendation != null -> {
                    DetailScreen(
                        recommendation = uiState.selectedRecommendation,
                        onBack = onBackFromDetail
                    )
                }
                uiState.selectedCategory != null -> {
                    RecommendationListScreen(
                        category = uiState.selectedCategory,
                        recommendations = uiState.recommendationsForCategory,
                        onRecommendationClick = onRecommendationSelected,
                        onBack = {}   // En tablet no hay "atrás" del panel derecho
                    )
                }
                else -> {
                    // Pantalla vacía cuando aún no eligieron categoría
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = androidx.compose.ui.Alignment.Center
                    ) {
                        Text(
                            text = "Selecciona una categoría",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}