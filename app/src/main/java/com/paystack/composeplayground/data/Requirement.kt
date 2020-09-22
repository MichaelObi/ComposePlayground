package com.paystack.composeplayground.data

import java.time.LocalDate

sealed class Requirement(
    open val id: String,
    open val text: String
)

data class TextRequirement(
    override val id: String,
    override val text: String
) : Requirement(id, text)

data class SingleChoiceRequirement(
    override val id: String,
    override val text: String,
    val options: List<Option>
) : Requirement(id, text)

data class DateRequirement(
    override val id: String,
    override val text: String,
    val minDate: LocalDate,
    val maxDate: LocalDate = LocalDate.now(),
) : Requirement(id, text)