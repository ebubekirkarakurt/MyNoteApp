package com.example.mynoteapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.ekasoftware.mynoteapp.R
import com.ekasoftware.mynoteapp.databinding.FragmentAddNoteBinding
import com.example.mynoteapp.model.Note
import com.example.mynoteapp.asset.toast
import com.example.mynoteapp.viewmodel.NotesViewModel
import com.google.android.material.snackbar.Snackbar



class AddNoteFragment : Fragment() {

    private lateinit var addNoteViewModel :NotesViewModel
    private lateinit var mView : View

    private var _binding: FragmentAddNoteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddNoteBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addNoteViewModel = (activity as MainActivity).noteViewModel
        mView = view

        binding.myToolbar.inflateMenu(R.menu.add_note)

        binding.backButton.setOnClickListener { view ->
            val action = AddNoteFragmentDirections.actionAddNoteFragmentToNotesFragment()
            Navigation.findNavController(view).navigate(action)
        }

        binding.myToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_save -> {
                    saveNote(mView)
                    true
                }
                else -> false
            }
        }
    }

    private fun saveNote(view: View) {
        val note_Title = binding.addNotetitle.text

        if (note_Title.isNotEmpty()) {

            val noteTitle = binding.addNotetitle.text.toString().trim()
            val noteBody = binding.addNotecomment.text.toString().trim()

            val note = Note(0, noteTitle, noteBody)

           addNoteViewModel.addNote(note)
            Snackbar.make(
                view,
                "Note saved successfully..",
                Snackbar.LENGTH_SHORT
            ).show()
            view.findNavController().navigate(R.id.action_addNoteFragment_to_notesFragment)


        } else {
            activity?.toast("Please enter note title!")
        }
    }
}

