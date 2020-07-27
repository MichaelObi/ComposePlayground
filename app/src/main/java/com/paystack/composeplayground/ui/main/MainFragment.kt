package com.paystack.composeplayground.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import androidx.compose.Composable
import androidx.compose.Recomposer
import androidx.compose.getValue
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.ui.core.Modifier
import androidx.ui.core.setContent
import androidx.ui.foundation.ScrollableColumn
import androidx.ui.foundation.Text
import androidx.ui.foundation.TextField
import androidx.ui.input.TextFieldValue
import androidx.ui.layout.InnerPadding
import androidx.ui.layout.Spacer
import androidx.ui.layout.preferredHeight
import androidx.ui.livedata.observeAsState
import androidx.ui.material.MaterialTheme
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import com.paystack.composeplayground.data.Question
import com.paystack.composeplayground.data.Question.MultipleChoiceQuestion
import com.paystack.composeplayground.data.Question.TextQuestion

class MainFragment : Fragment() {


    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FrameLayout(requireContext()).apply {
            layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT)
            composeExam()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

    }

    private fun ViewGroup.composeExam() = setContent(Recomposer.current()) {
        Form()
    }

    @Preview
    @Composable
    private fun Form() {
        MaterialTheme {
            TextField(TextFieldValue(), {})
            val viewState by viewModel.state.observeAsState()
            viewState?.let { QuestionList(viewState = it) }
        }
    }

    @Composable
    fun QuestionList(viewState: MainViewState) {
        ScrollableColumn(contentPadding = InnerPadding(start = 16.dp, end = 16.dp)) {
            val questions = viewState.questions
            questions.forEach {
                Spacer(modifier = Modifier.preferredHeight(16.dp))
                Text(text = it.text, style = MaterialTheme.typography.subtitle2)
                AnswerField(question = it)
            }
        }
    }

    @Composable
    fun AnswerField(question: Question) {
        when (question) {
            is TextQuestion -> TextAnswer()
            is MultipleChoiceQuestion -> TODO()
        }
    }

    @Composable
    fun TextAnswer() {
        Spacer(modifier = Modifier.preferredHeight(8.dp))
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}