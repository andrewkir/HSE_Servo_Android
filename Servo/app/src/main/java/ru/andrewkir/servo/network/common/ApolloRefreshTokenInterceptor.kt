package ru.andrewkir.servo.network.common

import android.content.Context
import com.apollographql.apollo3.api.http.HttpRequest
import com.apollographql.apollo3.api.http.HttpResponse
import com.apollographql.apollo3.network.http.HttpInterceptor
import com.apollographql.apollo3.network.http.HttpInterceptorChain
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import ru.andrewkir.servo.common.BaseRepository
import ru.andrewkir.servo.common.UserPrefsManager
import ru.andrewkir.servo.network.ApolloProvider

class ApolloRefreshTokenInterceptor(private val context: Context) : HttpInterceptor, BaseRepository() {
    private val mutex = Mutex()
    private val prefsManager = UserPrefsManager(context)

    override suspend fun intercept(
        request: HttpRequest,
        chain: HttpInterceptorChain
    ): HttpResponse {
        var token = mutex.withLock {
            prefsManager.refreshToken
        }

        val response =
            chain.proceed(request.newBuilder().addHeader("Authorization", "Bearer $token").build())

        return if (response.statusCode == 401) {
            val api = ApolloProvider(context)
            when (val tokensResponse =
                protectedApiCall(
                    api.refreshSession(token ?: "")
                )
            ) {
                is ApiResponse.OnSuccessResponse -> {
                    prefsManager.accessToken =
                        tokensResponse.value.data?.refreshSession?.accessToken
                    prefsManager.refreshToken =
                        tokensResponse.value.data?.refreshSession?.refreshToken
                }
                else -> null
            }
            chain.proceed(
                request.newBuilder()
                    .addHeader("Authorization", "Bearer ${prefsManager.accessToken}").build()
            )
        } else {
            response
        }
    }
}