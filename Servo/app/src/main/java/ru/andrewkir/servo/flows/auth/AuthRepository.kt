package ru.andrewkir.servo.flows.auth

import ru.andrewkir.servo.common.BaseRepository
import ru.andrewkir.servo.network.RickAndMortyClient

class AuthRepository(
    private val api: RickAndMortyClient
) : BaseRepository() {

    suspend fun loginByEmail(
        email: String,
        password: String
    ) = protectedApiCall(api.getCharacters(0))
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