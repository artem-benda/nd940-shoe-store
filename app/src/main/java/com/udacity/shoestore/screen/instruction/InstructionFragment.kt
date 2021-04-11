package com.udacity.shoestore.screen.instruction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.udacity.shoestore.R

class InstructionFragment : Fragment() {

    companion object {
        fun newInstance() = InstructionFragment()
    }

    private lateinit var viewModel: InstructionViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.instruction_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(InstructionViewModel::class.java)
        // TODO: Use the ViewModel
    }
}
