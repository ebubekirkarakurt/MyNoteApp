package com.example.mynoteapp.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.mynoteapp.model.Note

@Dao
interface NoteDao {

    @Query("SELECT * FROM NOTES  ORDER BY id ASC")
    fun getAllNotes() : LiveData<List<Note>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("SELECT * FROM NOTES WHERE title LIKE :query OR comment LIKE :query")
    fun searchNote(query: String?): LiveData<List<Note>>

}