package com.paystack.composeplayground.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.ExperimentalFocus
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.paystack.composeplayground.data.*

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

    @Composable
    private fun Form() {
        val viewState by viewModel.state.observeAsState()

        MaterialTheme {
            viewState?.let { QuestionList(it.requirements) }
        }
    }

    @Composable
    fun QuestionList(requirements: List<Requirement>) {
        ScrollableColumn(contentPadding = InnerPadding(start = 16.dp, end = 16.dp)) {
            requirements.forEach {
                Spacer(modifier = Modifier.preferredHeight(16.dp))
                Text(text = it.text, style = MaterialTheme.typography.subtitle2)
                AnswerField(requirement = it)
            }
        }
    }

    @Composable
    fun AnswerField(requirement: Requirement) {
        when (requirement) {
            is TextRequirement -> TextAnswer()
            is SingleChoiceRequirement -> SelectAnswer(requirement.options)
            is DateRequirement -> DateAnswer()
        }
    }

    @Composable
    fun TextAnswer() {
        Spacer(modifier = Modifier.preferredHeight(4.dp))
        OutlinedTextField(
            value = TextFieldValue(),
            onValueChange = {},
            label = {},
            modifier = Modifier.fillMaxWidth()
        )
    }

    @Composable
    fun DateAnswer() {
        Spacer(modifier = Modifier.preferredHeight(8.dp))
    }


    @OptIn(ExperimentalFocus::class)
    @Composable
    fun SelectAnswer(options: List<Option>) {
        var expanded by remember { mutableStateOf(false) }
        var selected: Option? by remember { mutableStateOf(null) }

        val textField = @Composable {
            Box(
                shape = RoundedCornerShape(4.dp),
                border = BorderStroke(0.5.dp, androidx.compose.ui.graphics.Color.Black),
                modifier = Modifier.fillMaxWidth()
                    .padding(top = 8.dp)
                    .wrapContentHeight().clickable(onClick = { expanded = true })
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth().padding(8.dp, 16.dp),
                    text = selected?.text ?: " ",
                )
            }
        }
        DropdownMenu(
            toggle = textField,
            expanded = expanded,
            onDismissRequest = { expanded = false },
            toggleModifier = Modifier.fillMaxWidth()
        ) {
            options.forEach { option ->
                val onClick = {
                    selected = option
                    expanded = false
                }
                DropdownMenuItem(onClick, Modifier.fillMaxWidth()) {
                    Text(text = option.text)
                }
            }

        }
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}