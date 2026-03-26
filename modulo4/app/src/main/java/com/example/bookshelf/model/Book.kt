package com.example.bookshelf.model

import com.google.gson.annotations.SerializedName

// La API de Google Books devuelve un JSON con esta estructura:
// { "items": [ { "id": "...", "volumeInfo": { "title": "...", "imageLinks": {...} } } ] }
// Gson mapea cada campo del JSON a estas data classes automáticamente.

// Respuesta raíz de la API — contiene la lista de libros
data class BooksResponse(
    @SerializedName("items")
    val items: List<BookItem> = emptyList()
)

// Cada libro en la lista tiene un id y su información en volumeInfo
data class BookItem(
    @SerializedName("id")
    val id: String,
    @SerializedName("volumeInfo")
    val volumeInfo: VolumeInfo
)

// La info del libro: título e imágenes
data class VolumeInfo(
    @SerializedName("title")
    val title: String = "Sin título",
    @SerializedName("imageLinks")
    val imageLinks: ImageLinks? = null  // Nullable porque no todos los libros tienen imagen
)

// URLs de las imágenes disponibles
data class ImageLinks(
    @SerializedName("thumbnail")
    val thumbnail: String? = null
) {
    // La API devuelve URLs con http:// pero Android bloquea tráfico no seguro.
    // Este getter reemplaza http por https automáticamente cada vez que se accede.
    val secureThumbail: String?
        get() = thumbnail?.replace("http://", "https://")
}