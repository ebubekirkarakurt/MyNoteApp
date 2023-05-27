package com.example.mynoteapp.view

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.ekasoftware.mynoteapp.R
import com.ekasoftware.mynoteapp.databinding.FragmentUpdateNoteBinding
import com.example.mynoteapp.asset.toast
import com.example.mynoteapp.model.Note
import com.example.mynoteapp.viewmodel.NotesViewModel


class UpdateNoteFragment : Fragment() {

    private var _binding: FragmentUpdateNoteBinding? = null
    private val binding get() = _binding!!

    private lateinit var updatedViewModel : NotesViewModel

    private val args : UpdateNoteFragmentArgs by navArgs()
    private lateinit var currentNote : Note

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdateNoteBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updatedViewModel = (activity as MainActivity).noteViewModel
        currentNote = args.note

        binding.updateNotetitle.setText(currentNote.title)
        binding.updateNotecomment.setText(currentNote.comment)

        binding.myUpdateToolbar.inflateMenu(R.menu.update_note)

        binding.backButton.setOnClickListener { view ->
            updateNote()
            val action = UpdateNoteFragmentDirections.actionUpdateNoteFragmentToNotesFragment()
            Navigation.findNavController(view).navigate(action)
        }


        binding.myUpdateToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.deletenote_button -> {
                    deleteNote()
                    true
                }
                else -> {false}
            }
        }

    }

    private fun updateNote(){

        val updatedNote_Title = binding.updateNotetitle.text.toString().trim()

        if(updatedNote_Title.isNotEmpty()){

            val updatedNoteTitle = binding.updateNotetitle.text.toString().trim()
            val updatedNoteComment = binding.updateNotecomment.text.toString().trim()

            val note = Note(currentNote.id, updatedNoteTitle, updatedNoteComment)
            updatedViewModel.updateNote(note)

            activity?.toast("Note updated successfully..")

        }
        else{
            activity?.toast("Please enter note title!")
        }
    }
    private fun deleteNote(){

        AlertDialog.Builder(activity).apply {
            setTitle("Delete Note")
            setMessage("Are you sure you want to permanently delete this note?")
            setPositiveButton("DELETE") { _, _ ->
                updatedViewModel.deleteNote(currentNote)
                view?.findNavController()?.
                navigate(
                    R.id.action_updateNoteFragment_to_notesFragment
                )
                activity?.toast("Note delete successfully..")
            }
            setNegativeButton("CANCEL", null)
        }.create().show()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}