// In package: com.example.studentnotes/

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Defines the database structure: list of entities (tables) and version
@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    // Companion object for Singleton pattern
    companion object {
        @Volatile // Ensures the instance is always up-to-date across threads
        private var INSTANCE: NoteDatabase? = null

        fun getDatabase(context: Context): NoteDatabase {
            // If INSTANCE is not null, return it; otherwise, create it
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java,
                    "student_notes_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}