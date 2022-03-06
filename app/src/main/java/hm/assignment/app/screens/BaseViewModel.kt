package hm.assignment.app.screens

import androidx.lifecycle.ViewModel
import hm.assignment.app.util.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * Created on 2022-03-05.
 * CopyrightⒸ Kagge
 */
abstract class BaseViewModel<T>: ViewModel() {
    protected val viewModelUiState = MutableStateFlow<UiState<T>>(UiState.Loading)
    protected val viewModelObjectState = MutableStateFlow<UiState<T>>(UiState.Loading)
    val uiState: StateFlow<UiState<T>> = viewModelUiState
    val objectState: StateFlow<UiState<T>> = viewModelObjectState
}