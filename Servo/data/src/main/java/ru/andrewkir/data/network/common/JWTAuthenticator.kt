package ru.andrewkir.data.network.common

import android.content.Context
import android.content.Intent
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import ru.andrewkir.data.common.AuthActivity
import ru.andrewkir.data.common.UserPrefsManager
import ru.andrewkir.data.network.ApolloProvider
import ru.andrewkir.domain.model.ApiResponse
import ru.andrewkir.domain.repositories.BaseRepository


class JWTAuthenticator(
    private val context: Context,
) : Authenticator, BaseRepository() {

    private val prefsManager = UserPrefsManager(context)


    override fun authenticate(route: Route?, response: Response): Request? {
        return runBlocking {
            val refreshToken = prefsManager.refreshToken
            val api = ApolloProvider(context)
            when (val tokensResponse =
                protectedApiCall(
                    api.refreshSession(refreshToken ?: "")
                )
            ) {
                is ApiResponse.OnSuccessResponse -> {
                    prefsManager.accessToken =
                        tokensResponse.value.data?.refreshSession?.accessToken
                    prefsManager.refreshToken =
                        tokensResponse.value.data?.refreshSession?.refreshToken
                    response.request.newBuilder()
                        .header("Authorization", "Bearer ${prefsManager.accessToken}")
                        .build()
                }
                else -> {
                    prefsManager.clearUser()
                    context.applicationContext.startActivity(
                        Intent(
                            context,
                            AuthActivity::class.java
                        )
                    )
                    null
                }
            }
        }
    }
}