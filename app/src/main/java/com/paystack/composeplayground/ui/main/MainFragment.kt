package com.paystack.composeplayground.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Recomposer
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.setContent
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.paystack.composeplayground.R
import com.paystack.composeplayground.data.Answer
import com.paystack.composeplayground.ui.theme.PlaygroundTheme

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels { MainViewModelFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FrameLayout(requireContext()).apply {
            layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT)
            setContent(Recomposer.current()) {
                MaterialTheme {
                    Surface(modifier = Modifier.fillMaxSize()) {
                        viewModel.state.observeAsState().value?.let { state ->
                            Scaffold(
                                topBar = { FormTopAppBar(R.string.compliance) { } },
                                bodyContent = { innerPadding ->
                                    OnboardingScreen(
                                        requirements = state.requirements,
                                        answers = state.answers,
                                        modifier = Modifier.fillMaxSize()
                                            .padding(innerPadding)
                                    ) { requirementId: RequirementId, answer: Answer ->
                                        viewModel.answerChanged(requirementId, answer)
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }


    companion object {
        fun newInstance() = MainFragment()
    }
}