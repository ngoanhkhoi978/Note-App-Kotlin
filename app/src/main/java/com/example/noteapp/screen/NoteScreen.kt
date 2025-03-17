import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.noteapp.viewmodel.NoteViewModel
import com.example.noteapp.data.Note
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete

@Composable
fun NoteScreen(viewModel: NoteViewModel, navController: NavController) {
    val notes by viewModel.allNotes.collectAsState(initial = emptyList())

    var noteToDelete by remember { mutableStateOf<Note?>(null) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate("add_note")
            }) {
                Text("+")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            LazyColumn {
                items(notes) { note ->
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(8.dp),
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = note.title, fontWeight = FontWeight.Bold)
                                Text(text = note.content)
                            }
                            IconButton(
                                onClick = { noteToDelete = note }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Delete Note"
                                )
                            }
                        }
                    }
                }
            }
        }

        if (noteToDelete != null) {
            AlertDialog(
                onDismissRequest = { noteToDelete = null },
                title = { Text("Xác nhận xoá") },
                text = { Text("Bạn có chắc chắn muốn xoá ghi chú này không?") },
                confirmButton = {
                    TextButton(onClick = {
                        noteToDelete?.let { viewModel.delete(it) }
                        noteToDelete = null
                    }) {
                        Text("Xóa", color = MaterialTheme.colorScheme.error)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { noteToDelete = null }) {
                        Text("Hủy")
                    }
                }
            )
        }
    }
}
