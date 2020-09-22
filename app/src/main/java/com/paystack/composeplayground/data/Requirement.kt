package com.paystack.composeplayground.data

import java.time.LocalDate

sealed class Requirement(
    open val id: String,
    open val text: String
)

data class TextRequirement(
    override val id: String,
    override val text: String,
    val placeholder: String? = null
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

data class FileRequirement(
    override val id: String,
    override val text: String,
    val supportedMimeTypes: List<String>,
    val maxFileSize: Long = 2000000
) : Requirement(id, text)