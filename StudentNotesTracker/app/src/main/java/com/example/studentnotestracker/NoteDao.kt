// NoteDao.kt - Defines C, R, U, D methods
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow // For observing data changes (R)

@Dao
interface NoteDao {

    // 1. CREATE (C) - Insert a new note
    @Insert
    suspend fun insert(note: Note)

    // 2. READ (R) - Get all notes, ordered by timestamp
    // Flow allows the UI to automatically update when data changes.
    @Query("SELECT * FROM notes ORDER BY timestamp DESC")
    fun getAllNotes(): Flow<List<Note>>

    // 3. UPDATE (U) - Update an existing note
    @Update
    suspend fun update(note: Note)

    // 4. DELETE (D) - Delete a specific note
    @Delete
    suspend fun delete(note: Note)

    // 5. READ (R) - Get a single note by ID
    @Query("SELECT * FROM notes WHERE id = :id")
    suspend fun getNoteById(id: Int): Note
}