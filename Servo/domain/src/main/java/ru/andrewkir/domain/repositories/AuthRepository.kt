package ru.andrewkir.domain.repositories

import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Optional
import ru.andrewkir.domain.SigninUserMutation
import ru.andrewkir.domain.model.ApiResponse

interface AuthRepository {
    suspend fun loginByEmail(
        email: String,
        password: String
    ): ApiResponse<ApolloResponse<SigninUserMutation.Data>>

    suspend fun loginByUsername(
        username: String,
        password: String
    ): ApiResponse<ApolloResponse<SigninUserMutation.Data>>
}