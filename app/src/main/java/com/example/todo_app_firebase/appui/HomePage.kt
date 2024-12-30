


package com.example.todo_app_firebase.appui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(navController: NavHostController) {
    var subject by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    val todoViewModel = TodoViewModel() // Corrected naming

    val context = LocalContext.current // Moved out of the Button for reuse

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Add New Note",
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
                            val noteData = NoteData(
                                subject = subject,
                                description = description
                            )

                            todoViewModel.insertNote(
                                noteData,
                                onSuccess = {
                                    Toast.makeText(
                                        context,
                                        "Note added successfully!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    subject = "" // Clear fields after success
                                    description = ""
                                },
                                onFailure = {
                                    Toast.makeText(
                                        context,
                                        "Failed to add note.",
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
                        text = "Save Note",
                        style = TextStyle(color = Color.White)
                    )
                }

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
                        text = "Show all Notes",
                        style = TextStyle(color = Color.White)
                    )
                }
            }
        }
    )
}
