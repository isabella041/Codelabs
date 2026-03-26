package com.example.bookshelf.data

import com.example.bookshelf.network.BookApi

// AppContainer centraliza la creación de dependencias.
// En vez de que cada clase cree sus propias instancias, las pide aquí.
// Esto hace que sea fácil reemplazar dependencias reales por falsas en tests.
interface AppContainer {
    val bookRepository: BookRepository
}

// Implementación real usada cuando corre la app normalmente.
class DefaultAppContainer : AppContainer {

    // El repositorio recibe el servicio de red ya configurado.
    // Si mañana cambiamos de Retrofit a otra librería, solo tocamos este archivo.
    override val bookRepository: BookRepository by lazy {
        NetworkBookRepository(BookApi.service)
    }
}