package com.example.mynoteapp.data

import com.example.mynoteapp.model.Note

class NoteRepository(private val db : NoteDatabase) {

    suspend fun insertNote(note: Note) = db.getNotesFromDao().addNote(note)
    suspend fun deleteNote(note: Note) = db.getNotesFromDao().deleteNote(note)
    suspend fun updateNote(note: Note) = db.getNotesFromDao().updateNote(note)
    fun getAllNotes() = db.getNotesFromDao().getAllNotes()
    fun searchNote(query : String?) = db.getNotesFromDao().searchNote(query)

}