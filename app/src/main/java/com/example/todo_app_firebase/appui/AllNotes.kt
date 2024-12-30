

package com.example.todo_app_firebase.appui
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.todo_app_firebase.firebasedb.TodoViewModel



@Composable
fun AllNotes(navController: NavHostController, viewModel: TodoViewModel) {
   // var viewModel=TodoViewModel()
    val notes by viewModel.NoteList.collectAsState() // Observe the notes list
   // val notes by viewModel.NoteList.collectAsState()
    val context = LocalContext.current
  //  Log.d("Notes", "Fetched Notes: $notes")


    var noteToDelete by remember { mutableStateOf("") }


    var showdilogbox = remember { mutableStateOf(false) }

    //var context= LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.getNotes() // Fetch notes when the composable is first displayed
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "All Notes",
            color = Color.Black,
            fontSize = 24.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.padding(top = 32.dp)
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(notes) { note ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .shadow(elevation = 8.dp, shape = RoundedCornerShape(16.dp)),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color(0xFFE3F2FD), // Light Blue
                                        Color(0xFFFFF9C4) // Light Yellow
                                    )
                                )
                            )
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier.weight(1f) // Take up remaining space
                        ) {
                            Text(
                                text = "Subject: ${note.subject}",
                                fontSize = 20.sp,
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )

                            Text(
                                text = "Description: ${note.description}",
                                fontSize = 18.sp,
                                color = Color.Black,
                                modifier = Modifier.padding(bottom = 4.dp)
                            )
                        }

                        Row(
                            modifier = Modifier.padding(start = 16.dp),
                            horizontalArrangement = Arrangement.End
                        ) {
//                            Icon(
//
//                                imageVector = Icons.Default.Edit,
//                                contentDescription = "Edit",
//                                tint = Color.Blue,
//                                modifier = Modifier
//                                    .size(30.dp)
//                                    .clickable {
//                                       navController.navigate("UpdateNote/${note.uniqueId}")
//                                    }
//                                    .padding(4.dp)
//                            )


                            // Edit button with custom styling
                            IconButton(
                                onClick = {
                                    Log.d("Navigation", "Edit button clicked")
                                    Log.d("Navigation", "UniqueId: ${note.uniqueId}")
                                    navController.navigate("UpdateNote/${note.uniqueId}")
                                },
                                modifier = Modifier.size(40.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "Edit",
                                    tint = Color.Blue,
                                    modifier = Modifier
                                        .size(30.dp)
                                        .padding(4.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete",
                                tint = Color.Red,
                                modifier = Modifier
                                    .size(30.dp)
                                    .clickable {
                                        noteToDelete=note.uniqueId
                                       showdilogbox.value=true
                                    }
                                    .padding(4.dp)
                            )


                        }
                    }
                }
            }
        }
    }
    if(showdilogbox.value==true){

        Log.d("Iddddd:","Note id{${noteToDelete}}")
        Delet_Serviice_Dialog(
            noteid =noteToDelete
            ,viewModel
            ,navController
            ,context
            , showDialog = showdilogbox.value,
            onDismiss = { showdilogbox.value = false }
        )
    }
}






//Delete Service DialogBox
@Composable
fun Delet_Serviice_Dialog(
    noteid: String,
    viewModel: TodoViewModel,
    navController: NavHostController,
    context: Context,
    showDialog: Boolean,
    onDismiss: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(text = "Delet Note Confirmation") },
            text = { Text("Are you sure you want to Note Delet?") },
            confirmButton = {
                Button(onClick = {
                    // Perform Delet action
                    viewModel.deleteNote(noteid,
                        onSuccess = {
                            Toast.makeText(context, "Note deleted successfully", Toast.LENGTH_SHORT).show()
                        },
                        onFailure = { errorMessage ->
                            Toast.makeText(context, "Error: $errorMessage", Toast.LENGTH_SHORT).show()
                        }
                    )

                    onDismiss() // Close the dialog
                },  colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red, // Background color for dismiss button
                    contentColor = Color.White   // Text color for dismiss button
                )) {
                    Text("Delet")
                }
            },
            dismissButton = {
                Button(onClick = { onDismiss() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Blue, // Background color for dismiss button
                        contentColor = Color.White   // Text color for dismiss button
                    )
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}