package com.paystack.composeplayground.ui.main

import com.paystack.composeplayground.data.Answer
import com.paystack.composeplayground.data.Requirement

typealias RequirementId = String

data class MainViewState(
    val requirements: List<Requirement>,
    val answers: Map<RequirementId, Answer>
)