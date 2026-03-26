
package com.example.affirmations

// Importaciones necesarias para Jetpack Compose y los recursos del proyecto
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.affirmations.data.Datasource
import com.example.affirmations.model.Affirmation
import com.example.affirmations.ui.theme.AffirmationsTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // El punto de entrada de la UI. Aquí cargo el tema personalizado de la app.
        setContent {
            AffirmationsTheme {
                // Uso un Surface para que el fondo se adapte automáticamente al color del tema
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Llamo a la función principal que arranca la app
                    AffirmationsApp()
                }
            }
        }
    }
}

@Composable
fun AffirmationsApp() {
    // Aquí conecto los datos con la interfaz. Cargo la lista desde el Datasource.
    AffirmationList(
        affirmationList = Datasource().loadAffirmations(),
    )
}

/**
 * Este componente es el encargado de mostrar la lista deslizable.
 * Uso LazyColumn porque es mucho más eficiente que un Column normal;
 * solo renderiza lo que se ve en pantalla.
 */
@Composable
fun AffirmationList(affirmationList: List<Affirmation>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        // Recorro la lista de afirmaciones y por cada una creo una "tarjeta"
        items(affirmationList) { affirmation ->
            AffirmationCard(
                affirmation = affirmation,
                modifier = Modifier.padding(8.dp) // Un poco de aire entre tarjetas
            )
        }
    }
}

/**
 * Aquí defino cómo se ve cada tarjeta individual.
 * Contiene una imagen arriba y el texto descriptivo abajo.
 */
@Composable
fun AffirmationCard(affirmation: Affirmation, modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Column {
            // Cargo la imagen usando el ID de recurso que viene en el objeto Affirmation
            Image(
                painter = painterResource(affirmation.imageResourceId),
                contentDescription = stringResource(affirmation.stringResourceId),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(194.dp),
                contentScale = ContentScale.Crop // Hago que la imagen llene el espacio sin deformarse
            )
            // El texto de la afirmación con un estilo tipográfico limpio
            Text(
                text = LocalContext.current.getString(affirmation.stringResourceId),
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}

// Una vista previa rápida para no tener que correr el emulador cada vez que cambio el diseño
@Preview
@Composable
private fun AffirmationCardPreview() {
    AffirmationCard(Affirmation(R.string.affirmation1, R.drawable.image1))
}
