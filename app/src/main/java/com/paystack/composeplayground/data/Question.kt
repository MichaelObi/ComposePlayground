package com.paystack.composeplayground.data

sealed class Question(
    open val id: String,
    open val text: String
) {

    data class TextQuestion(
        override val id: String,
        override val text: String
    ) : Question(id, text)

    data class MultipleChoiceQuestion(
        override val id: String,
        override val text: String,
        val options: List<Option>
    ) : Question(id, text)
}