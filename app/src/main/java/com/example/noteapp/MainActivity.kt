package com.example.noteapp

import NoteScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.noteapp.data.NoteDatabase
import com.example.noteapp.data.NoteRepository
import com.example.noteapp.screen.AddNoteScreen
import com.example.noteapp.ui.theme.NoteAppTheme
import com.example.noteapp.viewmodel.NoteViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val db = NoteDatabase.getDatabase(this)
            val repository = NoteRepository(db.noteDao())
            val viewModel = NoteViewModel(repository)
            NoteApp(navController, viewModel)
        }
    }
}

@Composable
fun NoteApp(navController: NavController, viewModel: NoteViewModel) {
    NavHost(navController as NavHostController, startDestination = "note_list") {
        composable("note_list") { NoteScreen(viewModel, navController)  }
        composable("add_note") { AddNoteScreen(viewModel) { navController.popBackStack() } }
    }
}

