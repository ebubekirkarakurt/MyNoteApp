package com.example.mynoteapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynoteapp.model.Note
import com.example.mynoteapp.data.NoteRepository
import kotlinx.coroutines.launch

class NotesViewModel(app: Application,
                     private val noteRepository: NoteRepository) :
                     AndroidViewModel(app){


    fun getAllNote() = noteRepository.getAllNotes()

    fun addNote(note: Note) = viewModelScope.launch {
        noteRepository.insertNote(note)
    }

    fun updateNote(note: Note) = viewModelScope.launch {
        noteRepository.updateNote(note)
    }

    fun deleteNote(note: Note) = viewModelScope.launch {
        noteRepository.deleteNote(note)
    }

    fun searchNote(query: String?) = noteRepository.searchNote(query)





}