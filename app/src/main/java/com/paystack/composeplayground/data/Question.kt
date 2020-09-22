package com.paystack.composeplayground.data

import java.time.LocalDate

sealed class Question(
    open val id: String,
    open val text: String
)

data class TextQuestion(
    override val id: String,
    override val text: String
) : Question(id, text)

data class SingleChoiceQuestion(
    override val id: String,
    override val text: String,
    val options: List<Option>
) : Question(id, text)

data class DateQuestion(
    override val id: String,
    override val text: String,
    val minDate: LocalDate,
    val maxDate: LocalDate = LocalDate.now(),
) : Question(id, text)
