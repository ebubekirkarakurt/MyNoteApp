package com.example.mynoteapp.view

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Switch
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.Navigation
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ekasoftware.mynoteapp.R
import com.ekasoftware.mynoteapp.databinding.FragmentNotesBinding
import com.example.mynoteapp.adapter.NoteAdapter
import com.example.mynoteapp.asset.toast
import com.example.mynoteapp.model.Note
import com.example.mynoteapp.viewmodel.NotesViewModel


class NotesFragment : Fragment() {


    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!

    private lateinit var notesViewModel: NotesViewModel
    private lateinit var noteAdapter: NoteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        notesViewModel = (activity as MainActivity).noteViewModel

        setUpRecyclerView()
        changeTheme(view)

        binding.addNoteButton.setBackgroundColor(Color.TRANSPARENT)

        binding.addNoteButton.setOnClickListener {
            val action = NotesFragmentDirections.actionNotesFragmentToAddNoteFragment()
            Navigation.findNavController(it).navigate(action)
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    searchNote(newText)
                }
                return true
            }

        })
    }

    private fun searchNote(note: String?) {
        val searchQuery = "%$note%"
        notesViewModel.searchNote(searchQuery).observe(
            this
        ) { list ->
            noteAdapter.differ.submitList(list)
        }
    }

    private fun setUpRecyclerView() {
        noteAdapter = NoteAdapter()

        binding.recyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(
                2,
                StaggeredGridLayoutManager.VERTICAL
            )
            setHasFixedSize(true)
            adapter = noteAdapter
        }

        activity?.let {
            notesViewModel.getAllNote().observe(viewLifecycleOwner) { note ->
                noteAdapter.differ.submitList(note)
                updateUI(note)
            }
        }

    }

    private fun updateUI(note: List<Note>) {
        if (note.isNotEmpty()) {
            binding.noNotesAvaliable.visibility = View.GONE
            binding.recyclerView.visibility = View.VISIBLE
            binding.notesToolbar.visibility = View.VISIBLE

        } else {
            binding.noNotesAvaliable.visibility = View.VISIBLE
            binding.recyclerView.visibility = View.GONE
            binding.notesToolbar.visibility = View.VISIBLE
        }

    }


    @SuppressLint("ResourceAsColor")
    private fun changeTheme(view : View){

        val btn : Switch = view.findViewById(R.id.changeTheme)

        btn.setOnCheckedChangeListener { buttonView, isChecked ->
            if(btn.isChecked){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                activity?.toast("Dark mode enabled")
            }
            else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                activity?.toast("Light mode enabled")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}

