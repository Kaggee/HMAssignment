package hm.assignment.app.api

import hm.assignment.app.util.log
import hm.assignment.app.util.logError
import retrofit2.HttpException

/**
 * Created on 2022-03-04.
 * CopyrightⒸ Kagge
 */
abstract class BaseApiRepository {
    protected suspend fun <T> safeApiCall(call: suspend() -> T): ApiResultWrapper<T> {
        return try {
            ApiResultWrapper.Success(call.invoke())
        } catch (t: Throwable) {
            t.localizedMessage?.log()
            t.logError()
            // Add code for non-fatal crashes here, to be able to analyze api-calls that fails.
            when(t) {
                is HttpException -> {
                    ApiResultWrapper.GenericError(t.code(), t.localizedMessage)
                }
                else -> {
                    ApiResultWrapper.NetworkError
                }
            }
        }
    }
}