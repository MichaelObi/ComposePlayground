package com.paystack.composeplayground.data

sealed class Answer

data class TextAnswer(val text: String): Answer()
data class OptionAnswer(val optionId: String): Answer()
data class FileAnswer(val fileUrl: String, val mimeType: String? = null): Answer()