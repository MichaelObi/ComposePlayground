package com.paystack.composeplayground.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.paystack.composeplayground.data.Question
import com.paystack.composeplayground.data.Question.TextQuestion
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.*

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModel: ViewModel() {
    private val _state = MutableStateFlow(MainViewState(listOf()))
    val state: LiveData<MainViewState>
        get() = _state.asLiveData()

    fun currentState() = _state.value

    init {
        val questions = listOf<Question>(
            TextQuestion(UUID.randomUUID().toString(), "How many goals did Jesse Lingard score in the 2019/2020 season?"),
            TextQuestion(UUID.randomUUID().toString(), "What English team has won the highest number of premier league games?")

        )
        _state.value = currentState().copy(questions = questions)
    }
}