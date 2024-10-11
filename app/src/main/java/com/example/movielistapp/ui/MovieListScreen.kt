package com.example.movielistapp.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.text.input.TextFieldValue
import com.example.movielistapp.data.model.Movie
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import com.example.movielistapp.ui.viewmodel.MovieViewModel

@Composable
@Preview
fun MovieListScreen(viewModel: MovieViewModel = viewModel()) {
    val movies by viewModel.allMovies.observeAsState(emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .background(Color(0xFFF0F0F0)), // Couleur de fond douce
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Liste des films à voir",
            style = MaterialTheme.typography.h6.copy(color = Color(0xFF6200EE)), // Couleur de titre
            modifier = Modifier
                .padding(bottom = 20.dp)
                .padding(5.dp)
        )

        LazyColumn {
            items(movies) { movie ->
                MovieItem(
                    movie = movie,
                    onWatched = { viewModel.markAsWatched(movie) },
                    onDelete = { viewModel.deleteMovie(movie) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        var title by remember { mutableStateOf(TextFieldValue("")) }

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Ajouter un film") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF6200EE), // Couleur de la bordure quand le champ est focalisé
                unfocusedBorderColor = Color.Gray, // Couleur de la bordure quand le champ n'est pas focalisé
                focusedLabelColor = Color(0xFF6200EE), // Couleur de l'étiquette quand le champ est focalisé
                unfocusedLabelColor = Color.Gray // Couleur de l'étiquette quand le champ n'est pas focalisé
            )
        )


        Button(
            onClick = {
                if (title.text.isNotEmpty()) {
                    viewModel.addMovie(title.text)
                    title = TextFieldValue("") // Réinitialiser le champ après l'ajout
                }
            },
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF6200EE)) // Couleur du bouton
        ) {
            Text("Ajouter", color = Color.White)
        }
    }
}

@Composable
fun MovieItem(movie: Movie, onWatched: () -> Unit, onDelete: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth() // Utilise `fillMaxWidth` au lieu de `fillMaxSize` pour ne pas prendre toute la hauteur
            .padding(vertical = 4.dp, horizontal = 8.dp) // Réduis le padding externe
            .border(BorderStroke(1.dp, Color.Gray), MaterialTheme.shapes.small)
            .background(if (movie.isWatched) Color(0xFFE0E0E0) else Color.White)
            .padding(8.dp), // Réduis le padding interne à 8.dp
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = movie.title,
            style = MaterialTheme.typography.body1.copy(
                color = if (movie.isWatched) Color.Gray else Color.Black,
                textDecoration = if (movie.isWatched) TextDecoration.LineThrough else null
            )
        )
        Row {
            IconButton(onClick = onWatched) {
                Icon(
                    Icons.Default.Check,
                    contentDescription = "Marquer comme vu",
                    tint = Color(0xFF6200EE)
                ) // Couleur des icônes
            }
            IconButton(onClick = onDelete) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Supprimer",
                    tint = Color(0xFF6200EE)
                ) // Couleur des icônes
            }
        }
    }
}
