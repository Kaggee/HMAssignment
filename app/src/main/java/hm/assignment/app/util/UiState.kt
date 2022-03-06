package hm.assignment.app.util

/**
 * Created on 2022-03-05.
 * CopyrightⒸ Kagge
 */
sealed class UiState<out T> {
    object Loading: UiState<Nothing>()
    data class Success<out T>(val data: T) : UiState<T>()
}
