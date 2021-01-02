package dev.michaelobi.clubhouse.ui.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel : ViewModel() {
    private val _state = MutableStateFlow(HomeViewState())
    val state: StateFlow<HomeViewState>
        get() = _state

    init {
        _state.value =
            _state.value.copy(avatarUrl = "https://pbs.twimg.com/profile_images/1336833599249723393/PmtoIkMd_400x400.jpg")
    }
}