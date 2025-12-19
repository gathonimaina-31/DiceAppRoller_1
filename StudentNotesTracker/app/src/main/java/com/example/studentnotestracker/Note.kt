import androidx.room.Entity
    import androidx.room.PrimaryKey

    @Entity(tableName = "notes") // 1. Defines this class as a Room table
    data class Note(
        val title: String,
        val content: String,
        val subject: String,
        val timestamp: Long = System.currentTimeMillis(), // Stores C/U time

        @PrimaryKey(autoGenerate = true) // 2. Auto-incrementing unique ID
        val id: Int = 0
    )
}