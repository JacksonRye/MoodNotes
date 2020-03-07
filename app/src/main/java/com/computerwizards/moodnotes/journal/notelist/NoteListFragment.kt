package com.computerwizards.moodnotes.journal.notelist

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.computerwizards.moodnotes.R
import com.computerwizards.moodnotes.database.getDatabase
import com.computerwizards.moodnotes.databinding.NoteListFragmentBinding


class NoteListFragment : Fragment() {

    private lateinit var viewModel: NoteListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = NoteListFragmentBinding.inflate(inflater)

        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(
            this, NoteListViewModel.Factory(
                getDatabase(requireContext()).noteDao
            )
        )
            .get(NoteListViewModel::class.java)

        binding.viewModel = viewModel

        viewModel.navigateToNote.observe(viewLifecycleOwner, Observer {
            it?.let {
                this.findNavController().navigate(
                    NoteListFragmentDirections.actionNoteListFragmentToNoteFragment(it)
                )
                viewModel.onNavigated()
            }
        })

        val adapter = NoteListAdapter(NoteClickListener { note ->
            this.findNavController().navigate(
                NoteListFragmentDirections.actionNoteListFragmentToNoteFragment(note)
            )
            viewModel.onNavigated()
        })

        binding.noteList.adapter = adapter


        viewModel.noteList.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
            Log.d("NoteListFrag", "${it.size}")
        })

        setHasOptionsMenu(true)

        return binding.root

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        return inflater.inflate(R.menu.fragment_list_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.clear -> {
                viewModel.clear(); true
            }
            else -> super.onOptionsItemSelected(item)

        }
    }


}
