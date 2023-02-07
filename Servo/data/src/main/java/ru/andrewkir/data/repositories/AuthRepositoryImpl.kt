package ru.andrewkir.data.repositories

import com.apollographql.apollo3.api.Optional
import ru.andrewkir.data.network.ApolloProvider
import ru.andrewkir.domain.repositories.AuthRepository
import ru.andrewkir.domain.repositories.BaseRepository
import ru.andrewkir.domain.type.UserSigninInput

class AuthRepositoryImpl(
    private val api: ApolloProvider
) : AuthRepository, BaseRepository() {

    override suspend fun loginByEmail(
        email: String,
        password: String
    ) = protectedApiCall(
        api.signInUser(
            UserSigninInput(
                email = Optional.presentIfNotNull(email),
                password = password
            )
        )
    )

    override suspend fun loginByUsername(
        username: String,
        password: String
    ) = protectedApiCall(
        api.signInUser(
            UserSigninInput(
                username = Optional.presentIfNotNull(username),
                password = password
            )
        )
    )
//
//    suspend fun register(
//        email: String,
//        username: String,
//        password: String
//    ) = protectedApiCall {
//        api.getCharacters(0)
//    }
}