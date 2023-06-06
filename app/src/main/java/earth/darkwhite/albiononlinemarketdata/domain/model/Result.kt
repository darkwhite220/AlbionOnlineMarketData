package earth.darkwhite.albiononlinemarketdata.domain.model

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

sealed class Result<out T> {
  object Loading : Result<Nothing>()
  
  data class Success<out T>(val data: T) : Result<T>()
  
  data class Error(val e: String) : Result<Nothing>()
}

sealed class ResultSample<out T> {
  
  data class Success<out T>(val data: T) : ResultSample<T>()
  
  data class Failure(val e: String) : ResultSample<Nothing>()
}

fun <T> Flow<T>.asResult(): Flow<Result<T>> {
  return this
    .map<T, Result<T>> {
      Result.Success(it)
    }
    .onStart { emit(Result.Loading) }
    .catch { emit(Result.Error(it.localizedMessage.orEmpty())) }
}