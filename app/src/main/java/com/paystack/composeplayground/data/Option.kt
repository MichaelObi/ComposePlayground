package com.paystack.composeplayground.data

data class Option(
    val id: String,
    val text: String,
    val requirements: List<Requirement> = emptyList()
)