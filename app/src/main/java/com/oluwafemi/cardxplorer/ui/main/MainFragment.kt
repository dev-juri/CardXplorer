package com.oluwafemi.cardxplorer.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.oluwafemi.cardxplorer.databinding.FragmentMainBinding
import com.oluwafemi.cardxplorer.util.isOnline
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()

        binding.cardNumberInputField.editText?.doAfterTextChanged {
            val numberString = it.toString().trim()
            if (numberString.length >= 8) {
                if (isOnline(requireContext())) {
                    viewModel.fetchCardDetails(numberString)
                } else {
                    Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show()
                }
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
                        binding.cardNumberInputField.editText?.isEnabled = true
                    }
                    LoadingState.ERROR -> {
                        binding.cardNumberInputField.editText?.isEnabled = true
                        binding.progressBar.visibility = View.GONE
                    }
                    else -> {
                        binding.cardNumberInputField.editText?.isEnabled = false
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
                binding.cardType.text = cardDetails.type
                binding.cardBrand.text = cardDetails.scheme
                binding.cardCountry.text =
                    "${cardDetails.country?.name} ${cardDetails.country?.emoji}"
            } else {
                binding.cardType.clearComposingText()
                binding.cardBrand.clearComposingText()
                binding.cardCountry.clearComposingText()
            }
        }
    }
}