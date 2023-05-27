package com.example.mynoteapp.view

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.ekasoftware.mynoteapp.databinding.ActivityMainBinding
import com.example.mynoteapp.data.NoteDatabase
import com.example.mynoteapp.data.NoteRepository
import com.example.mynoteapp.viewmodel.NoteViewModelProviderFactory
import com.example.mynoteapp.viewmodel.NotesViewModel

class MainActivity : AppCompatActivity() {

    lateinit var noteViewModel: NotesViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        setUpViewModel()
    }

    private fun setUpViewModel() {
        val noteRepository = NoteRepository(
            NoteDatabase(this)
        )

        val viewModelProviderFactory =
            NoteViewModelProviderFactory(
                application, noteRepository
            )

        noteViewModel = ViewModelProvider(
            this,
            viewModelProviderFactory
        ).get(NotesViewModel::class.java)
    }


}