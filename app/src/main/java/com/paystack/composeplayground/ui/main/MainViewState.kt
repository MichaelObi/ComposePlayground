package com.paystack.composeplayground.ui.main

import com.paystack.composeplayground.data.Answer
import com.paystack.composeplayground.data.Requirement

typealias RequirementId = String

data class MainViewState(
    var currentRequirementIndex: Int = 0,
    val requirements: List<Requirement> = listOf(),
    val answers: Map<RequirementId, Answer> = mapOf()
)