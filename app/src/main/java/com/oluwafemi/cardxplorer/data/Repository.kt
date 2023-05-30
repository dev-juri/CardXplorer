package com.oluwafemi.cardxplorer.data

import com.oluwafemi.cardxplorer.data.model.CardDetails
import com.oluwafemi.cardxplorer.util.NetworkResult

interface Repository {
    suspend fun fetchCardDetails(cardNumber: String): NetworkResult<CardDetails?>
}