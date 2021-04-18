package com.udacity.shoestore.screen.shoedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.shoestore.databinding.ShoeDetailFragmentBinding
import com.udacity.shoestore.screen.SharedViewModel
import com.udacity.shoestore.screen.welcome.WelcomeFragmentDirections

class ShoeDetailFragment : Fragment() {

    companion object {
        fun newInstance() = ShoeDetailFragment()
    }

    val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var viewModel: ShoeDetailViewModel
    private lateinit var binding: ShoeDetailFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ShoeDetailFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        val factory = ShoeDetailViewModelFactory(sharedViewModel)
        viewModel = ViewModelProvider(this, factory).get(ShoeDetailViewModel::class.java)
        viewModel.shouldNavigateToList.observe(
            viewLifecycleOwner,
            Observer { shouldNavigate ->
                if (shouldNavigate) {
                    findNavController().navigate(WelcomeFragmentDirections.actionGlobalShoeListFragment())
                    viewModel.onNavigateToListComplete()
                }
            }
        )
        binding.viewModel = viewModel

        return binding.root
    }
}
