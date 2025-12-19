package com.example.studentnotestracker

import android.app.Application
import com.example.studentnotestracker.NoteDatabase
import com.example.studentnotestracker.NoteRepository


class StudentNotesTracker : Application() {

    val database by lazy { NoteDatabase.getDatabase(this) }

    val repository by lazy { NoteRepository(database.noteDao()) }
}