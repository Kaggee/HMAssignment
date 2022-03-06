package hm.assignment.app.api

/**
 * Class for determining success of API-calls, used with safeApiCall to "never" crash :)
 */
sealed class ApiResultWrapper<out T> {
    data class Success<out T>(val value: T): ApiResultWrapper<T>()
    data class GenericError(val code: Int?, val message: String?): ApiResultWrapper<Nothing>()
    object NetworkError: ApiResultWrapper<Nothing>()
}