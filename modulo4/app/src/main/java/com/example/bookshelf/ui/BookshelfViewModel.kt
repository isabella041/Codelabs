package com.example.bookshelf.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.bookshelf.BookshelfApplication
import com.example.bookshelf.data.BookRepository
import com.example.bookshelf.model.BookItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// El UiState representa todos los estados posibles de la pantalla.
// Sealed interface garantiza que no puede haber un estado no contemplado.
sealed interface BookshelfUiState {
    // Cargando: la solicitud a la API está en curso
    object Loading : BookshelfUiState
    // Éxito: tenemos la lista de libros para mostrar
    data class Success(val books: List<BookItem>) : BookshelfUiState
    // Error: algo salió mal (sin internet, API caída, etc.)
    data class Error(val message: String) : BookshelfUiState
}

class BookshelfViewModel(
    private val bookRepository: BookRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<BookshelfUiState>(BookshelfUiState.Loading)
    val uiState: StateFlow<BookshelfUiState> = _uiState.asStateFlow()

    // Lanzamos la búsqueda apenas se crea el ViewModel
    init {
        getBooks()
    }

    // viewModelScope garantiza que la corrutina se cancela
    // automáticamente cuando el ViewModel se destruye.
    fun getBooks(query: String = "jazz history") {
        viewModelScope.launch {
            _uiState.value = BookshelfUiState.Loading
            try {
                val books = bookRepository.getBooks(query)
                _uiState.value = BookshelfUiState.Success(books)
            } catch (e: Exception) {
                _uiState.value = BookshelfUiState.Error(
                    e.message ?: "Error desconocido"
                )
            }
        }
    }

    companion object {
        // Factory personalizado que le inyecta el repositorio al ViewModel.
        // Sin esto, Android no sabría cómo crear el ViewModel con parámetros.
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as BookshelfApplication
                BookshelfViewModel(application.container.bookRepository)
            }
        }
    }
}