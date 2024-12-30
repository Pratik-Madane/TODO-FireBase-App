package com.example.todo_app_firebase.firebasedb

import androidx.lifecycle.ViewModel
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID
/*
class Todo_Viewmodel:ViewModel() {

    private val database=FirebaseDatabase.getInstance().getReference("Notes")

    fun inserNotes(allData: NoteData, OnSuccess: () -> Unit, OnFailure: () -> Unit) {

        val unicqid=UUID.randomUUID().toString()

        database.child(unicqid).setValue(allData)
            .addOnSuccessListener { OnSuccess()  }
            .addOnFailureListener {  OnFailure()}
    }
}
*/



class TodoViewModel : ViewModel() {

    private val database = FirebaseDatabase.getInstance().getReference("Notes")

    fun insertNote(
        noteData: NoteData,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {
        val uniqueIdTemp = UUID.randomUUID().toString()


        val NoteWithId = noteData.copy(uniqueId = uniqueIdTemp)

        database.child(uniqueIdTemp).setValue(NoteWithId)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure() }
    }

    val allNotes= MutableStateFlow<List<NoteData>>(emptyList())
    val NoteList:StateFlow<List<NoteData>>get()=allNotes.asStateFlow()

    fun getNotes(){

        database.get().addOnSuccessListener {
            Snapshot->
            val Note=Snapshot.children.mapNotNull { it.getValue(NoteData::class.java) }
            allNotes.value=Note

        }.addOnFailureListener {

        }

    }
    fun updateNote(noteId:String,note: NoteData, onSuccess: () -> Unit, onFailure: () -> Unit) {
        // Firebase update logic here
        database.child(noteId).setValue(note)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure() }

    }
    fun deleteNote(noteId:String, onSuccess: () -> Unit, onFailure: (Any?) -> Unit){

        database.child(noteId).removeValue()
            .addOnSuccessListener {
                onSuccess()
                getNotes() // Fetch the updated list of notes
            }
            .addOnFailureListener { exception ->
                onFailure(exception.message ?: "Error deleting services")
            }
    }


}
