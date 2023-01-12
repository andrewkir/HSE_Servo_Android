package ru.andrewkir.servo.network.common

import android.content.Context
import android.util.Log
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.http.HttpRequest
import com.apollographql.apollo3.api.http.HttpResponse
import com.apollographql.apollo3.network.http.HttpInterceptor
import com.apollographql.apollo3.network.http.HttpInterceptorChain
import com.apollographql.apollo3.network.http.LoggingInterceptor
import com.apollographql.apollo3.network.okHttpClient
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import ru.andrewkir.servo.BuildConfig
import ru.andrewkir.servo.common.UserPrefsManager
import java.util.concurrent.TimeUnit

open class BaseApolloClient {
    protected fun apolloClient(context: Context): ApolloClient =
        ApolloClient
            .Builder()
            .serverUrl(BASE_URL)
            .addHttpInterceptor(LoggingInterceptor { str: String -> Log.d("APOLLO", str) })
            .addHttpInterceptor(
                ApolloRefreshTokenInterceptor(
                    context
                )
            )
//            .okHttpClient(
//                provideOkHTPPClient(
//                    JWTAuthenticator(context),
//                    UserPrefsManager(context).accessToken ?: "",
//                    UserPrefsManager(context).refreshToken ?: ""
//                )
//            )
            .httpExposeErrorBody(true)
            .build()

    class AuthorizationInterceptor(val token: String) : HttpInterceptor {
        override suspend fun intercept(
            request: HttpRequest,
            chain: HttpInterceptorChain
        ): HttpResponse {
            return chain.proceed(
                request.newBuilder().addHeader("Authorization", "Bearer $token").build()
            )
        }
    }

    private fun provideOkHTPPClient(
        authenticator: JWTAuthenticator? = null,
        accessToken: String? = null,
        refreshToken: String? = null
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                chain.proceed(
                    chain.request().newBuilder().also {
                        it.addHeader("Accept", "application/json")
                        if (accessToken != null) {
                            it.addHeader(
                                "Authorization",
                                "Bearer $accessToken"
                            )
                        }
                        if (refreshToken != null) {
                            it.addHeader(
                                "x-refresh-token",
                                "$refreshToken"
                            )
                        }
                    }.build()
                )
            }
            .also { client ->
                if (BuildConfig.DEBUG) {
                    val logging = HttpLoggingInterceptor()
                    logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                    client.addInterceptor(logging)
                }
                if (authenticator != null) {
                    client.authenticator(authenticator)
                }
            }.build()
    }
}