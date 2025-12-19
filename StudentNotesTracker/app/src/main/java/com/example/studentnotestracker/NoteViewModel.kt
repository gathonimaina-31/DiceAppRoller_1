// In package: com.example.studentnotestracker/

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.studentnotestracker.NoteRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

// 1. Primary ViewModel class
class NoteViewModel(private val repository: NoteRepository) : ViewModel() {

    // READ (R): Expose the Flow of all notes to the UI.
    // stateIn converts the Flow into a StateFlow, making it lifecycle-aware for Compose.
    val allNotes = repository.allNotes.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    // CREATE (C)
    fun insert(title: String, content: String, subject: String) = viewModelScope.launch {
        // Simple validation/data processing can go here
        val newNote = Note(title = title, content = content, subject = subject)
        repository.insert(newNote)
    }

    // UPDATE (U)
    fun update(id: Int, title: String, content: String, subject: String) = viewModelScope.launch {
        // Read the existing note, update fields, and save back
        val existingNote = repository.getNoteById(id)
        existingNote?.let {
            val updatedNote = it.copy(
                title = title,
                content = content,
                subject = subject,
                timestamp = System.currentTimeMillis() // Update the revision time
            )
            repository.update(updatedNote)
        }
    }

    // DELETE (D)
    fun delete(note: Note) = viewModelScope.launch {
        repository.delete(note)
    }

    // READ (R) - Specific note for editing
    suspend fun getNoteById(id: Int) = repository.getNoteById(id)
}

// --- Factory for creating the ViewModel ---
class NoteViewModelFactory(private val repository: NoteRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NoteViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}