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
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.ExperimentalFocus
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focusObserver
import androidx.compose.ui.graphics.Color
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
            Column {
                val title = @Composable {
                    Text(text = "Compliance")
                }
                TopAppBar(title)
                viewState?.let { state ->
                    ScrollableColumn(contentPadding = InnerPadding(start = 16.dp, end = 16.dp)) {
                        state.requirements.forEach {
                            RequirementField(requirement = it)
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun RequirementField(requirement: Requirement) {
        Spacer(modifier = Modifier.preferredHeight(16.dp))
        when (requirement) {
            is TextRequirement -> TextAnswer(requirement)
            is SingleChoiceRequirement -> SelectAnswer(requirement)
            is DateRequirement -> DateAnswer()
            is FileRequirement -> FileAnswer(requirement)
        }
    }

    @OptIn(ExperimentalFocus::class)
    @Composable
    fun TextAnswer(requirement: TextRequirement) {
        var hideHint by remember { mutableStateOf(true) }

        Text(text = requirement.text, style = MaterialTheme.typography.subtitle2)
        OutlinedTextField(
            value = TextFieldValue(),
            onValueChange = {},
            label = {
                if (!hideHint) {
                    Text(text = requirement.placeholder.orEmpty())
                }
            },
            modifier = Modifier.fillMaxWidth()
                .focusObserver { hideHint = it == FocusState.Active }
        )
    }

    @Composable
    fun DateAnswer() {
    }

    @Composable
    fun FileAnswer(requirement: FileRequirement) {
        Button(
            onClick = {},
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = requirement.text)
        }
    }


    @Composable
    fun SelectAnswer(requirement: SingleChoiceRequirement) {
        var expanded by remember { mutableStateOf(false) }
        var selected: Option? by remember { mutableStateOf(null) }

        val textField = @Composable {
            Box(
                shape = RoundedCornerShape(4.dp),
                border = BorderStroke(0.5.dp, Color.Black),
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

        Text(text = requirement.text, style = MaterialTheme.typography.subtitle2)

        DropdownMenu(
            toggle = textField,
            expanded = expanded,
            onDismissRequest = { expanded = false },
            toggleModifier = Modifier.fillMaxWidth()
        ) {
            requirement.options.forEach { option ->
                val onClick = {
                    selected = option
                    expanded = false
                }
                DropdownMenuItem(onClick, Modifier.fillMaxWidth()) {
                    Text(text = option.text)
                }
            }
        }

        selected?.requirements?.forEach {
            RequirementField(requirement = it)
        }
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}