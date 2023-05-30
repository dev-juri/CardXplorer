package com.oluwafemi.cardxplorer.data

import com.oluwafemi.cardxplorer.data.model.CardDetails
import com.oluwafemi.cardxplorer.data.remote.CardService
import com.oluwafemi.cardxplorer.util.NetworkResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val remoteSource: CardService,
    private val dispatcher: CoroutineDispatcher
) : Repository {

    override suspend fun fetchCardDetails(cardNumber: String): NetworkResult<CardDetails?> =
        withContext(dispatcher) {
            NetworkResult.Loading
            val result = remoteSource.getCardDetails(cardNumber)

            return@withContext if (result.isSuccessful && result.code() != 404) {
                val responseBody = result.body()
                NetworkResult.Success(responseBody)
            } else {
                if (result.code() == 404) {
                    NetworkResult.Error("Card details not found.")
                } else {
                    NetworkResult.Error("Connection problem, Please retry.")
                }
            }
        }
}