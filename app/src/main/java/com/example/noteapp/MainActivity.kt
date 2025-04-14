package com.example.noteapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.example.noteapp.data.NoteDatabase
import com.example.noteapp.data.NoteRepository
import com.example.noteapp.screen.AddNoteScreen
import com.example.noteapp.screen.EditNoteScreen
import com.example.noteapp.screen.NoteScreen
import com.example.noteapp.ui.theme.NoteAppTheme
import com.example.noteapp.viewmodel.NoteViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NoteAppTheme {
                val navController = rememberNavController()
                val db = NoteDatabase.getDatabase(this)
                val repository = NoteRepository(db.noteDao())
                val viewModel = NoteViewModel(repository)
                NoteApp(navController, viewModel)
            }
        }
    }
}

@Composable
fun NoteApp(navController: NavHostController, viewModel: NoteViewModel) {
    NavHost(navController = navController, startDestination = "note_list") {
        composable("note_list") {
            NoteScreen(viewModel, navController)
        }
        composable("add_note") {
            AddNoteScreen(viewModel) { navController.popBackStack() }
        }
        composable(
            route = "edit_note/{noteId}",
            arguments = listOf(navArgument("noteId") { type = NavType.IntType })
        ) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getInt("noteId") ?: 0
            EditNoteScreen(
                viewModel = viewModel,
                noteId = noteId,
                onBack = { navController.popBackStack() }
            )
        }
    }
}