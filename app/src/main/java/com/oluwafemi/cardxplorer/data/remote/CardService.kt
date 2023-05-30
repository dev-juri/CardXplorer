package com.oluwafemi.cardxplorer.data.remote

import com.oluwafemi.cardxplorer.data.model.CardDetails
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CardService {

    @GET("{cardNumber}")
    suspend fun getCardDetails(@Path("cardNumber") cardNumber: String): Response<CardDetails>
}