package com.paystack.composeplayground.ui.main

import androidx.annotation.StringRes
import androidx.compose.foundation.*
import androidx.compose.foundation.Icon
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.runtime.*
import androidx.compose.runtime.savedinstancestate.savedInstanceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.focusObserver
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.paystack.composeplayground.R
import com.paystack.composeplayground.data.*


@Composable
fun FormTopAppBar(
    @StringRes title: Int,
    onBackPressed: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = title),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackPressed) {
                Icon(Icons.Filled.ChevronLeft)
            }
        },
        // We need to balance the navigation icon, so we add a spacer.
        actions = {
            Spacer(modifier = Modifier.preferredWidth(68.dp))
        },
        backgroundColor = MaterialTheme.colors.surface,
        elevation = 0.dp
    )
}

@Composable
fun OnboardingScreen(
    requirements: List<Requirement>,
    answers: Map<RequirementId, Answer>,
    modifier: Modifier = Modifier,
    onAnswerChanged: (RequirementId, Answer) -> Unit
) {
    var currentRequirementIndex by savedInstanceState { 0 }
    val requirement = remember(currentRequirementIndex) { requirements[currentRequirementIndex] }
    val answer = answers[requirement.id]

    ScrollableColumn(modifier = modifier.then(Modifier.padding(16.dp))) {
        RequirementField(requirement, answer) { newAnswer ->
            onAnswerChanged(requirement.id, newAnswer)
        }

        Spacer(modifier = Modifier.height(20.dp))
        if (currentRequirementIndex > 0) {
            OutlinedButton(
                onClick = { currentRequirementIndex-- },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(id = R.string.previous))
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
        if (requirements.lastIndex > currentRequirementIndex) {
            OutlinedButton(
                onClick = {
                    currentRequirementIndex++
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(id = R.string.next))
            }
        }
    }
}

@Composable
fun RequirementField(requirement: Requirement, answer: Answer?, onAnswerChanged: (Answer) -> Unit) {
    Spacer(modifier = Modifier.preferredHeight(16.dp))
    when (requirement) {
        is TextRequirement -> TextFormField(requirement, answer as TextAnswer?) {
            onAnswerChanged(TextAnswer(it))
        }

        is SingleChoiceRequirement -> SingleChoiceField(requirement, answer as OptionAnswer?) {
            onAnswerChanged(OptionAnswer(it))
        }
        is DateRequirement -> DateAnswer()
        is FileRequirement -> FileField(requirement)
    }
}

@Composable
fun TextFormField(
    requirement: TextRequirement,
    answer: TextAnswer?,
    onValueChanged: (String) -> Unit
) {
    var hideHint by remember { mutableStateOf(true) }

    Text(text = requirement.text, style = MaterialTheme.typography.subtitle2)
    OutlinedTextField(
        value = answer?.text.orEmpty(),
        onValueChange = { onValueChanged(it) },
        label = {
            if (!hideHint) {
                Text(text = requirement.placeholder.orEmpty())
            }
        },
        modifier = Modifier.fillMaxWidth()
            .onFocusChanged { hideHint = it == FocusState.Active }
    )
}

@Composable
fun DateAnswer() {
}

@Composable
fun FileField(requirement: FileRequirement) {
    Button(
        onClick = {},
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = requirement.text)
    }
}


@Composable
fun SingleChoiceField(
    requirement: SingleChoiceRequirement,
    selectedAnswer: OptionAnswer?,
    onValueChanged: (Option) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    val textField = @Composable {
        Box(
            modifier = Modifier.fillMaxWidth()
                .border(BorderStroke(0.5.dp, MaterialTheme.colors.onSurface), RoundedCornerShape(4.dp))
                .wrapContentHeight()
                .clickable(onClick = { expanded = true })
        ) {
            Text(
                modifier = Modifier.fillMaxWidth().padding(8.dp, 16.dp),
                text = selectedAnswer?.option?.text.orEmpty(),
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
                onValueChanged(option)
                expanded = false
            }
            DropdownMenuItem(onClick, Modifier.fillMaxWidth()) {
                Text(text = option.text)
            }
        }
    }

    ChildRequirements(selectedAnswer)
}

@Composable
private fun ChildRequirements(selectedAnswer: OptionAnswer?) {
}