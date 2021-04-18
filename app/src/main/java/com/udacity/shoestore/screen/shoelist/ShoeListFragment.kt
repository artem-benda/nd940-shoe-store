package com.udacity.shoestore.screen.shoelist

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.ListItemShoeBinding
import com.udacity.shoestore.databinding.ShoeListFragmentBinding
import com.udacity.shoestore.screen.SharedViewModel

class ShoeListFragment : Fragment() {

    companion object {
        fun newInstance() = ShoeListFragment()
    }

    val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var viewModel: ShoeListViewModel
    private lateinit var binding: ShoeListFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ShoeListFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(this).get(ShoeListViewModel::class.java)
        viewModel.shouldNavigateToDetails.observe(
            viewLifecycleOwner,
            Observer { shouldNavigate ->
                if (shouldNavigate) {
                    findNavController().navigate(ShoeListFragmentDirections.actionShoeListFragmentToShoeDetailFragment())
                    viewModel.onNavigateToDetailsComplete()
                }
            }
        )
        sharedViewModel.shoeList.observe(
            viewLifecycleOwner,
            Observer {
                it?.let { shoeList ->
                    binding.shoeItemContainer.removeAllViews()
                    shoeList.forEach {
                        val itemBinding = ListItemShoeBinding.inflate(layoutInflater)
                        itemBinding.model = it
                        binding.shoeItemContainer.addView(itemBinding.root)
                    }
                }
            }
        )
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.actions, menu)
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> {
                sharedViewModel.logout()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
