package com.computerwizards.moodnotes.journal.noteui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.computerwizards.moodnotes.database.Note
import com.computerwizards.moodnotes.database.getDatabase
import com.computerwizards.moodnotes.databinding.NoteFragmentBinding
import com.google.android.material.snackbar.Snackbar

class NoteFragment : Fragment() {

    private lateinit var viewModel: NoteViewModel
    private lateinit var note: Note

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = NoteFragmentBinding.inflate(inflater)

        val args = NoteFragmentArgs.fromBundle(requireArguments())

        note = args.note

        viewModel = ViewModelProvider(
            this, NoteViewModel.Factory(
                getDatabase(requireContext()).noteDao, note
            )
        ).get(NoteViewModel::class.java)


        viewModel.selectedNote.observe(viewLifecycleOwner, Observer {
            Log.d("NoteFrag", "$it")
            binding.note = it
        })

        viewModel.saveNote.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                Snackbar.make(
                    activity!!.findViewById(android.R.id.content),
                    "Note Saved!", Snackbar.LENGTH_SHORT
                ).show()
                viewModel.savedNote()
            }
        })

        viewModel.navigateToMood.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                this.findNavController().navigate(
                    NoteFragmentDirections.actionNoteFragmentToMoodFragment(note)
                )
                viewModel.navigateToMoodComplete()
            }
        })


        binding.viewModel = viewModel

        return binding.root
    }


}
