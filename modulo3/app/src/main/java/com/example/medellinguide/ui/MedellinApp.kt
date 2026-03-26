package com.example.medellinguide.ui

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.medellinguide.viewmodel.MedellinViewModel


// Rutas de navegación definidas como constantes para evitar typos en strings.
object MedellinDestinations {
    const val HOME = "home"
    const val LIST = "list"
    const val DETAIL = "detail"
}

// MedellinApp es el punto de entrada de la UI.
// Recibe el WindowWidthSizeClass para adaptar el layout según el tamaño de pantalla.
@Composable
fun MedellinApp(
    windowSize: WindowWidthSizeClass,
    viewModel: MedellinViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val navController: NavHostController = rememberNavController()

    // En pantallas grandes (tablets) mostramos un layout de dos paneles.
    // En pantallas compactas (móviles) usamos navegación apilada normal.
    val isExpandedScreen = windowSize == WindowWidthSizeClass.Expanded

    if (isExpandedScreen) {
        // Layout adaptable para tablet: categorías a la izquierda, contenido a la derecha
        MedellinAdaptiveLayout(
            uiState = uiState,
            onCategorySelected = { viewModel.onCategorySelected(it) },
            onRecommendationSelected = { viewModel.onRecommendationSelected(it) },
            onBackFromDetail = { viewModel.onBackFromDetail() }
        )
    } else {
        // Layout para móvil con NavigationBar inferior
        NavHost(
            navController = navController,
            startDestination = MedellinDestinations.HOME
        ) {
            composable(MedellinDestinations.HOME) {
                HomeScreen(
                    categories = uiState.categories,
                    onCategoryClick = { category ->
                        viewModel.onCategorySelected(category)
                        navController.navigate(MedellinDestinations.LIST)
                    }
                )
            }

            composable(MedellinDestinations.LIST) {
                RecommendationListScreen(
                    category = uiState.selectedCategory,
                    recommendations = uiState.recommendationsForCategory,
                    onRecommendationClick = { rec ->
                        viewModel.onRecommendationSelected(rec)
                        navController.navigate(MedellinDestinations.DETAIL)
                    },
                    onBack = {
                        viewModel.onBackToHome()
                        navController.popBackStack()
                    }
                )
            }

            composable(MedellinDestinations.DETAIL) {
                DetailScreen(
                    recommendation = uiState.selectedRecommendation,
                    onBack = {
                        viewModel.onBackFromDetail()
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}