package com.example.studentnotestracker

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateOf // <-- Essential for state variables
import androidx.compose.runtime.remember    // <-- Essential for remembering state
import androidx.compose.runtime.getValue     // <-- Essential for state access
import androidx.compose.runtime.setValue     // <-- Essential for state updates
import androidx.compose.runtime.LaunchedEffect // <-- Essential for loading data in U-mode
import com.example.studentnotestracker.Note // <-- FIXES 'Unresolved reference Note'
import com.example.studentnotestracker.NoteViewModel // <-- FIXES 'Unresolved reference NoteViewModel'

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditNoteScreen(
    noteId: Int, // 0 means CREATE, > 0 means UPDATE
    viewModel: NoteViewModel,
    onBack: () -> Unit // Function to navigate back to the list
) {
    val coroutineScope = rememberCoroutineScope()

    // State variables to hold user input for the form
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var subject by remember { mutableStateOf("") }

    val isEditMode = noteId != 0
    val screenTitle = if (isEditMode) "Edit Existing Note" else "Add New Note"

    // 1. Data Loading (UPDATE Mode)
    // LaunchedEffect runs code only once when the composable is first launched or if noteId changes.
    LaunchedEffect(noteId) {
        if (isEditMode) {
            // Fetch the existing note data on a background thread
            val note = viewModel.getNoteById(noteId)
            note?.let {
                // Populate the form fields with existing data
                title = it.title
                content = it.content
                subject = it.subject
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(screenTitle) },
                navigationIcon = {
                    // Back button uses the navigation callback
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()), // Allows scrolling for long notes/small screens
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // --- Input Fields ---

            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title (e.g., OOP Concepts)") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = subject,
                onValueChange = { subject = it },
                label = { Text("Subject (e.g., Programming I)") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = content,
                onValueChange = { content = it },
                label = { Text("Note Details") },
                minLines = 5,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(24.dp))

            // --- SAVE Button (C and U logic) ---
            Button(
                onClick = {
                    // Launch a coroutine to perform the database operation
                    coroutineScope.launch {
                        if (isEditMode) {
                            // Call the UPDATE operation in ViewModel
                            viewModel.update(noteId, title, content, subject)
                        } else {
                            // Call the CREATE operation in ViewModel
                            viewModel.insert(title, content, subject)
                        }
                        onBack() // Go back to the list screen after saving
                    }
                },
                // The button is only enabled if Title and Content are not blank
                enabled = title.isNotBlank() && content.isNotBlank(),
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                Text(if (isEditMode) "Update Note" else "Save New Note")
            }
        }
    }
}