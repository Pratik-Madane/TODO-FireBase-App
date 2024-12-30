package com.example.todo_app_firebase.NavController

//import AllNotes
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todo_app_firebase.appui.AllNotes
import com.example.todo_app_firebase.appui.HomePage
import com.example.todo_app_firebase.appui.UpdateNote
import com.example.todo_app_firebase.firebasedb.TodoViewModel

@Composable
fun AppNavController() {
    val navController = rememberNavController()
    val viewModel: TodoViewModel = viewModel() // Shared ViewModel for navigation

    NavHost(
        navController = navController,
        startDestination = Route.HomePage
    ) {
        composable(Route.HomePage) {
            HomePage(navController)
        }

        composable(Route.AllNotes) {
            AllNotes(navController, viewModel) // Pass the ViewModel to AllNotes
        }
//
//        composable("UpdateNote/{uniqueId}") { backStackEntry ->
//            val uniqueId = backStackEntry.arguments?.getString("uniqueId") ?: return@composable
//            UpdateNote(uniqueId, navController, viewModel) // Pass the ViewModel to UpdateNote
//        }

        composable("UpdateNote/{uniqueId}") { backStackEntry ->
            val uniqueId = backStackEntry.arguments?.getString("uniqueId") ?: ""
            UpdateNote(uniqueId = uniqueId, navController = navController, viewModel = viewModel)
        }

    }
}
