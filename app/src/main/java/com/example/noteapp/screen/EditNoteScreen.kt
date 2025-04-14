package com.example.noteapp.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.noteapp.data.Note
import com.example.noteapp.viewmodel.NoteViewModel
import androidx.compose.runtime.collectAsState

@Composable
fun EditNoteScreen(
    viewModel: NoteViewModel,
    noteId: Int,
    onBack: () -> Unit
) {
    val notes by viewModel.allNotes.collectAsState(initial = emptyList())

    if (notes.isEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
            Spacer(modifier = Modifier.height(8.dp))
            Text("Đang tải ghi chú...")
        }
        return
    }

    val note = notes.find { it.id == noteId }
    if (note == null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Không tìm thấy ghi chú!")
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onBack) {
                Text("Quay lại")
            }
        }
        return
    }

    var title by remember { mutableStateOf(note.title) }
    var content by remember { mutableStateOf(note.content) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Tiêu đề") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = content,
            onValueChange = { content = it },
            label = { Text("Nội dung") }
        )
        Spacer(modifier = Modifier.height(64.dp))
        Button(onClick = {
            if (title.isNotBlank() && content.isNotBlank()) {
                viewModel.update(Note(id = noteId, title = title, content = content))
                onBack()
            }
        }) {
            Text("Cập nhật ghi chú")
        }
    }
}