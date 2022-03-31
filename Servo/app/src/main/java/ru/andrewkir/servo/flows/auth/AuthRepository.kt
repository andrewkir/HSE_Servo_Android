package ru.andrewkir.servo.flows.auth

import ru.andrewkir.servo.common.BaseRepository
import ru.andrewkir.servo.network.ApolloProvider
import ru.andrewkir.type.UserCreateInput

class AuthRepository(
    private val api: ApolloProvider
) : BaseRepository() {

    suspend fun loginByEmail(
        email: String,
        password: String
    ) = protectedApiCall(api.signUpUser(UserCreateInput(
        email = email,
        username = email,
        password = password,
        firstName = email,
        lastName = email
    )))
//
//    suspend fun loginByUsername(
//        username: String,
//        password: String
//    ) = protectedApiCall {
//        api.getCharacters(0)
//    }
//
//    suspend fun register(
//        email: String,
//        username: String,
//        password: String
//    ) = protectedApiCall {
//        api.getCharacters(0)
//    }
}