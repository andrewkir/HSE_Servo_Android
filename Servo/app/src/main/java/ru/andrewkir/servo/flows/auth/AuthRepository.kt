package ru.andrewkir.servo.flows.auth

import ru.andrewkir.servo.common.BaseRepository
import ru.andrewkir.servo.network.ApolloProvider
import ru.andrewkir.type.UserSigninInput
import java.util.Optional

class AuthRepository(
    private val api: ApolloProvider
) : BaseRepository() {

    suspend fun loginByEmail(
        email: com.apollographql.apollo3.api.Optional<String>,
        password: String
    ) = protectedApiCall(
        api.signInUser(
            UserSigninInput(
                email = email,
                password = password
            )
        )
    )

    suspend fun loginByUsername(
        username: com.apollographql.apollo3.api.Optional<String>,
        password: String
    ) = protectedApiCall(
        api.signInUser(
            UserSigninInput(
                username = username,
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