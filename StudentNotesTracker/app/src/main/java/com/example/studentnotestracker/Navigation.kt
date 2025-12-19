package com.example.studentnotestracker

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

// Define routes and keys as constants for safety and consistency
object Destinations {
    const val LIST_ROUTE = "notes_list"
    // Route includes a path argument ({noteId})
    const val ADD_EDIT_ROUTE = "add_edit_note/{noteId}"
    const val NOTE_ID_KEY = "noteId"
}

@Composable
fun NoteAppNavigation(viewModel: NoteViewModel) {
    // 1. Controller: Manages the navigation state
    val navController = rememberNavController()

    // 2. NavHost: Links the controller to the destinations
    NavHost(
        navController = navController,
        startDestination = Destinations.LIST_ROUTE // Where the app opens first
    ) {
        // --- 1. NOTES LIST SCREEN (READ / DELETE) ---
        composable(Destinations.LIST_ROUTE) {
            NoteListScreen(
                viewModel = viewModel,
                // Action: FAB Click (CREATE) -> Navigate to Add/Edit screen passing '0'
                onAddNote = {
                    navController.navigate("add_edit_note/0")
                },
                // Action: Note Card Click (UPDATE) -> Navigate passing the note's ID
                onEditNote = { noteId ->
                    navController.navigate("add_edit_note/$noteId")
                }
            )
        }

        // --- 2. ADD/EDIT NOTE SCREEN (CREATE / UPDATE) ---
        composable(
            route = Destinations.ADD_EDIT_ROUTE,
            // Define the argument expected in the path
            arguments = listOf(navArgument(Destinations.NOTE_ID_KEY) {
                type = NavType.IntType
                defaultValue = 0
            })
        ) { backStackEntry ->
            // Retrieve the 'noteId' argument from the route
            val noteId = backStackEntry.arguments?.getInt(Destinations.NOTE_ID_KEY) ?: 0

            AddEditNoteScreen(
                noteId = noteId, // Pass the ID to the screen
                viewModel = viewModel,
                // Action: Back button or Save/Update complete -> Pop screen off stack
                onBack = { navController.popBackStack() }
            )
        }
    }
}