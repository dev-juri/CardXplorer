package com.oluwafemi.cardxplorer.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oluwafemi.cardxplorer.data.Repository
import com.oluwafemi.cardxplorer.data.model.CardDetails
import com.oluwafemi.cardxplorer.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _dataState = MutableLiveData<LoadingState?>(null)
    val dataState get() = _dataState

    private val _errorMessage = MutableLiveData<String?>(null)
    val errorMessage get() = _errorMessage

    private val _cardDetails = MutableLiveData<CardDetails>()
    val cardDetails get() = _cardDetails

    fun fetchCardDetails(cardNumber: String) {
        _dataState.postValue(LoadingState.LOADING)
        viewModelScope.launch {
            when (val result = repository.fetchCardDetails(cardNumber)) {
                is NetworkResult.Success -> {
                    _dataState.postValue(LoadingState.SUCCESS)
                    _cardDetails.postValue(result.data!!)
                }
                is NetworkResult.Error -> {
                    _dataState.postValue(LoadingState.ERROR)
                    _errorMessage.postValue(result.exception)
                }
                else -> {
                    _dataState.postValue(LoadingState.LOADING)
                }
            }
        }
    }

    fun resetState() {
        _dataState.postValue(null)
    }
    fun clearError() {
        _errorMessage.postValue(null)
    }
}

enum class LoadingState {
    SUCCESS,
    ERROR,
    LOADING
}