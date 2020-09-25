package com.paystack.composeplayground.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.paystack.composeplayground.data.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.*

fun randomId() = UUID.randomUUID().toString()

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModel : ViewModel() {
    private val _state = MutableStateFlow(MainViewState(listOf(), mapOf()))
    val state: LiveData<MainViewState>
        get() = _state.asLiveData()

    fun currentState() = _state.value

    fun answerChanged(requirementId: RequirementId, answer: Answer) {
        val answers = currentState().answers.toMutableMap().apply {
            put(requirementId, answer)
        }
        _state.value = currentState().copy(answers = answers)
    }

    init {
        _state.value = currentState().copy(requirements = requirements)
    }

    companion object {
        val requirements = listOf(
            TextRequirement(randomId(), "BVN", "1234567890"),
            SingleChoiceRequirement(
                randomId(),
                "What means of identification do you have?",
                listOf(
                    Option(
                        randomId(),
                        "International Passport",
                        listOf(
                            TextRequirement(
                                randomId(),
                                "Passport number",
                                "A1234567890"
                            ),
                            FileRequirement(
                                randomId(),
                                "Upload Passport",
                                listOf("image/*")
                            )
                        )
                    ),
                    Option(
                        randomId(),
                        "Driver's License",
                        listOf(
                            FileRequirement(
                                randomId(),
                                "Upload Driver's License",
                                listOf("image/*")
                            )
                        )
                    ),
                    Option(
                        randomId(),
                        "National ID",
                        listOf(
                            FileRequirement(
                                randomId(),
                                "Upload National ID",
                                listOf("image/*")
                            )
                        )
                    )
                )
            )
        )
    }
}

class MainViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}