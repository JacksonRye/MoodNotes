package com.computerwizards.moodnotes.online

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.computerwizards.moodnotes.databinding.OnlineFragmentBinding

class OnlineFragment : Fragment() {

    private lateinit var viewModel: OnlineViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        viewModel =
            ViewModelProvider(this, OnlineViewModel.Factory()).get(OnlineViewModel::class.java)

        val binding = OnlineFragmentBinding.inflate(inflater)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.response.observe(viewLifecycleOwner, Observer {
            binding.response = it
        })



        return binding.root
    }


}
