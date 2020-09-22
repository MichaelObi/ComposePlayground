package com.paystack.composeplayground.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.paystack.composeplayground.data.Option
import com.paystack.composeplayground.data.Question
import com.paystack.composeplayground.data.SingleChoiceQuestion
import com.paystack.composeplayground.data.TextQuestion
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.*

fun randomId() = UUID.randomUUID().toString()

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModel : ViewModel() {
    private val _state = MutableStateFlow(MainViewState(listOf()))
    val state: LiveData<MainViewState>
        get() = _state.asLiveData()

    fun currentState() = _state.value

    init {
        _state.value = currentState().copy(questions = questions)
    }

    companion object {
        val questions = listOf<Question>(
            TextQuestion(
                randomId(),
                "How many goals did Jesse Lingard score in the 2019/2020 season?"
            ),
            TextQuestion(
                randomId(),
                "What English team has won the highest number of premier league games?"
            ),

            SingleChoiceQuestion(
                randomId(),
                "What means of identification do you have?",
                listOf(
                    Option(randomId(), "International Passport"),
                    Option(randomId(), "Driver's License"),
                    Option(randomId(), "National ID")
                )
            )
        )
    }
}