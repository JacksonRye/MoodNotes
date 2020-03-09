package com.computerwizards.moodnotes.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.computerwizards.moodnotes.databinding.AboutFragmentBinding

// TODO Add Adviceslip api to AboutFragment

class AboutFragment : Fragment() {

    companion object {
        fun newInstance() = AboutFragment()
    }

    private lateinit var viewModel: AboutViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = AboutFragmentBinding.inflate(inflater)

        viewModel = ViewModelProvider(this, AboutViewModel.Factory())
            .get(AboutViewModel::class.java)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.response.observe(viewLifecycleOwner, Observer {
            binding.adviceText.text = it
        })

        return binding.root
    }


}
