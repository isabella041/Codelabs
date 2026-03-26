package com.example.bookshelf.network

import com.example.bookshelf.model.BooksResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

// URL base de la API de Google Books.
// Retrofit le agrega el resto del path según el método que se llame.
private const val BASE_URL = "https://www.googleapis.com/books/v1/"

// Instancia única de Retrofit configurada con Gson.
// lazy significa que solo se crea la primera vez que se necesita.
private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

// Interfaz que describe los endpoints disponibles.
// Retrofit genera automáticamente la implementación de esta interfaz.
interface BookApiService {

    // GET https://www.googleapis.com/books/v1/volumes?q=jazz+history
    // @Query("q") convierte el parámetro en ?q=... en la URL
    @GET("volumes")
    suspend fun getBooks(
        @Query("q") query: String = "jazz history"
    ): BooksResponse
}

// Singleton que expone el servicio listo para usar.
// El resto de la app usa BookApi.service.getBooks() directamente.
object BookApi {
    val service: BookApiService by lazy {
        retrofit.create(BookApiService::class.java)
    }
}