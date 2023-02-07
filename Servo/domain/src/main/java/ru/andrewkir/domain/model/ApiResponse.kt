package ru.andrewkir.domain.model

sealed class ApiResponse<out T> {
    data class OnSuccessResponse<T> (val value: T) : ApiResponse<T>()
    data class OnErrorResponse(
        val isNetworkFailure: Boolean,
        val code: Int?,
        val body: String?
    ) : ApiResponse<Nothing>()
}