package com.example.bookshelf.data

import com.example.bookshelf.model.BookItem
import com.example.bookshelf.network.BookApiService

// La interfaz define el contrato del repositorio.
// Tener una interfaz nos permite sustituir la implementación real
// por una falsa en los tests sin cambiar nada más en la app.
interface BookRepository {
    suspend fun getBooks(query: String): List<BookItem>
}

// Implementación real que llama a la API de Google Books.
// Recibe el servicio como parámetro (inyección de dependencias)
// en vez de crearlo aquí adentro — eso facilita el testing.
class NetworkBookRepository(
    private val bookApiService: BookApiService
) : BookRepository {

    override suspend fun getBooks(query: String): List<BookItem> {
        // Llamamos a la API y devolvemos solo la lista de libros.
        // Si items es null (respuesta vacía), devolvemos lista vacía.
        return bookApiService.getBooks(query).items
    }
}