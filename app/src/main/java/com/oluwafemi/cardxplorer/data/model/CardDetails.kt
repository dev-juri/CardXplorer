package com.oluwafemi.cardxplorer.data.model

data class CardDetails(
    val number: NumberDetails?,
    val scheme: String?,
    val type: String?,
    val brand: String?,
    val country: Country?,
    val bank: Bank?
)

data class NumberDetails(
    val length: Int?,
    val luhn: Boolean?
)

data class Country(
    val numeric: String?,
    val alpha2: String?,
    val name: String?,
    val emoji: String?,
    val currency: String?,
    val latitude: Int?,
    val longitude: Int?,
)

data class Bank(
    val name: String?,
    val url: String?,
    val phone: String?,
    val city: String?
)