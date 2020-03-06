package com.computerwizards.moodnotes.moodui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.computerwizards.moodnotes.database.getDatabase
import com.computerwizards.moodnotes.databinding.MoodFragmentBinding

class MoodFragment : Fragment() {


//    TODO("Set Toolbar Title")

    private lateinit var viewModel: MoodViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = MoodFragmentBinding.inflate(inflater)

        val args = MoodFragmentArgs.fromBundle(requireArguments())


        val note = args.note
        viewModel = ViewModelProvider(
            this, MoodViewModel.Factory(
                getDatabase(requireContext()).noteDao,
                note
            )
        )
            .get(MoodViewModel::class.java)

        viewModel.navigateToNote.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                this.findNavController().navigate(
                    MoodFragmentDirections.actionMoodFragmentToNoteFragment(note)
                )
                viewModel.navigateToNoteCompleted()
            }
        })

        binding.viewModel = viewModel

        binding.lifecycleOwner = this

        return binding.root
    }

}
