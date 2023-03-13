package ru.andrewkir.domain.repositories

import com.apollographql.apollo3.api.ApolloResponse
import ru.andrewkir.domain.SigninUserMutation
import ru.andrewkir.domain.SignupUserMutation
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

    suspend fun register(
        email: String,
        username: String,
        password: String,
        firstName: String,
        lastName: String
    ): ApiResponse<ApolloResponse<SignupUserMutation.Data>>
}