package com.oluwafemi.cardxplorer.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.oluwafemi.cardxplorer.R
import com.oluwafemi.cardxplorer.databinding.FragmentMainBinding
import com.oluwafemi.cardxplorer.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val binding by viewBinding(FragmentMainBinding::bind)
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()

        binding.cardNumberInputField.editText?.doAfterTextChanged {
            val numberString = it.toString().trim()
            if (numberString.length >= 8) {
                viewModel.fetchCardDetails(numberString)
            }
        }
    }

    private fun setupObservers() {
        viewModel.dataState.observe(viewLifecycleOwner) { state ->
            if (state != null) {
                when (state) {
                    LoadingState.SUCCESS -> {
                        binding.progressBar.visibility = View.GONE
                        viewModel.resetState()
                        viewModel.clearError()
                    }
                    LoadingState.ERROR -> {
                        binding.progressBar.visibility = View.GONE
                    }
                    else -> {
                        binding.progressBar.visibility = View.VISIBLE
                        viewModel.clearError()
                    }
                }
            } else {
                binding.progressBar.visibility = View.GONE
                viewModel.clearError()
            }
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            if (errorMessage != null) {
                Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
            }
        }

        viewModel.cardDetails.observe(viewLifecycleOwner) { cardDetails ->
            if (cardDetails != null) {
                binding.cardDetails.text = cardDetails.toString()
            }
        }
    }
}