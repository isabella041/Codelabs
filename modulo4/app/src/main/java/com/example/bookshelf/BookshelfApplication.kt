package com.example.bookshelf

import android.app.Application
import com.example.bookshelf.data.AppContainer
import com.example.bookshelf.data.DefaultAppContainer

// Application es el primer objeto que crea Android al iniciar la app.
// Lo usamos para inicializar el contenedor de dependencias una sola vez.
class BookshelfApplication : Application() {

    // container es accesible desde cualquier parte de la app
    // a través de (application as BookshelfApplication).container
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}