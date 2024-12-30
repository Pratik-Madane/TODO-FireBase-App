package com.example.todo_app_firebase.appui

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.todo_app_firebase.NavController.Route
import com.example.todo_app_firebase.firebasedb.NoteData
import com.example.todo_app_firebase.firebasedb.TodoViewModel

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun UpdateNote(uniqueId: String, navController: NavHostController) {
//
//    var subject by remember { mutableStateOf("") }
//    var description by remember { mutableStateOf("") }
//    val todoViewModel = TodoViewModel() // Corrected naming
//    val notelist by todoViewModel.NoteList.collectAsState()
//    val context = LocalContext.current // Moved out of the Button for reuse// Find the note with the uniqueId of "2"
//    val singleNote = notelist.find { it.uniqueId == uniqueId }
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = {
//                    Text(
//                        "Add New Note",
//                        style = MaterialTheme.typography.headlineLarge
//                    )
//                },
//                modifier = Modifier.background(MaterialTheme.colorScheme.onBackground)
//            )
//        },
//        content = { paddingValues ->
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(paddingValues)
//                    .padding(16.dp),
//                verticalArrangement = Arrangement.SpaceBetween
//            ) {
//
//
//                // Input Fields
//                Column(modifier = Modifier.fillMaxWidth()) {
//                    TextField(
//                        value = subject,
//                        onValueChange = { newValue ->
//
//                                // Check if mobile number matches any in the personDetail list
//                                notelist.find { it.subject == newValue }?.let { note ->
//                                    // Autofill name and comeFrom if match found
//                                    subject = note.subject ?: ""
//                                    description = note.description ?: ""
//                                }
//
//                        },
//                        label = { Text("Subject") },
//                        placeholder = { Text("Enter subject") },
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(bottom = 16.dp),
//                        singleLine = true,
//                        colors = TextFieldDefaults.textFieldColors(
//                            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
//                            unfocusedIndicatorColor = Color.Gray
//                        )
//                    )
//
//                    TextField(
//                        value = description,
//                        onValueChange = { description = it },
//                        label = { Text("Description") },
//                        placeholder = { Text("Enter detailed description") },
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(200.dp)
//                            .padding(bottom = 16.dp),
//                        colors = TextFieldDefaults.textFieldColors(
//                            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
//                            unfocusedIndicatorColor = Color.Gray
//                        ),
//                        maxLines = 5
//                    )
//                }
//
//                // Save Button
//                Button(
//                    onClick = {
//                        if (subject.isNotBlank() && description.isNotBlank()) {
//                            val noteData = NoteData(
//                                subject = subject,
//                                description = description
//                            )
//
//                            todoViewModel.insertNote(
//                                noteData,
//                                onSuccess = {
//                                    Toast.makeText(
//                                        context,
//                                        "Note added successfully!",
//                                        Toast.LENGTH_SHORT
//                                    ).show()
//                                    subject = "" // Clear fields after success
//                                    description = ""
//                                },
//                                onFailure = {
//                                    Toast.makeText(
//                                        context,
//                                        "Failed to add note.",
//                                        Toast.LENGTH_SHORT
//                                    ).show()
//                                }
//                            )
//                        } else {
//                            Toast.makeText(
//                                context,
//                                "Please fill in all fields.",
//                                Toast.LENGTH_SHORT
//                            ).show()
//                        }
//                    },
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(50.dp)
//                        .clip(RoundedCornerShape(8.dp)),
//                    colors = ButtonDefaults.buttonColors(
//                        containerColor = Color.Cyan,
//                        contentColor = Color.Black
//                    )
//                ) {
//                    Text(
//                        text = "Save Note",
//                        style = TextStyle(color = Color.White)
//                    )
//                }
//
//                Button(
//                    onClick = {
//
//                        navController.navigate(Route.AllNotes)
//                    },
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(50.dp)
//                        .clip(RoundedCornerShape(8.dp)),
//                    colors = ButtonDefaults.buttonColors(
//                        containerColor = Color.Cyan,
//                        contentColor = Color.Black
//                    )
//                ) {
//                    Text(
//                        text = "Show all Notes",
//                        style = TextStyle(color = Color.White)
//                    )
//                }
//            }
//        }
//    )
//
//}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateNote(uniqueId: String, navController: NavHostController, viewModel: TodoViewModel) {
    var subject by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    val notelist by viewModel.NoteList.collectAsState()
    val context = LocalContext.current


    // Find the note with the given uniqueId
    val singleNote = notelist.find { it.uniqueId == uniqueId }

    // Populate fields with existing note data if available
    LaunchedEffect(singleNote) {
        singleNote?.let {
            subject = it.subject ?: ""
            description = it.description ?: ""
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Update Note",
                        style = MaterialTheme.typography.headlineLarge
                    )
                },
                modifier = Modifier.background(MaterialTheme.colorScheme.onBackground)
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                // Input Fields
                Column(modifier = Modifier.fillMaxWidth()) {
                    TextField(
                        value = subject,
                        onValueChange = { subject = it },
                        label = { Text("Subject") },
                        placeholder = { Text("Enter subject") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        singleLine = true,
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                            unfocusedIndicatorColor = Color.Gray
                        )
                    )

                    TextField(
                        value = description,
                        onValueChange = { description = it },
                        label = { Text("Description") },
                        placeholder = { Text("Enter detailed description") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .padding(bottom = 16.dp),
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                            unfocusedIndicatorColor = Color.Gray
                        ),
                        maxLines = 5
                    )
                }

                // Save Button
                Button(
                    onClick = {
                        if (subject.isNotBlank() && description.isNotBlank()) {
                            val updatedNote = singleNote?.copy(
                                subject = subject,
                                description = description
                            )

                            viewModel.updateNote(
                                 singleNote!!.uniqueId,
                                updatedNote!!,
                                onSuccess = {
                                    Toast.makeText(
                                        context,
                                        "Note updated successfully!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    navController.navigate(Route.AllNotes)
                                },
                                onFailure = {
                                    Toast.makeText(
                                        context,
                                        "Failed to update note.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            )
                        } else {
                            Toast.makeText(
                                context,
                                "Please fill in all fields.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Cyan,
                        contentColor = Color.Black
                    )
                ) {
                    Text(
                        text = "Upadate Note",
                        style = TextStyle(color = Color.White)
                    )
                }

                // Show All Notes Button
                Button(
                    onClick = {
                        navController.navigate(Route.AllNotes)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Cyan,
                        contentColor = Color.Black
                    )
                ) {
                    Text(
                        text = "Show All Notes",
                        style = TextStyle(color = Color.White)
                    )
                }





            }
        }
    )
}



