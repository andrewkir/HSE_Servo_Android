package ru.andrewkir.data.network.common

import android.content.Context
import android.util.Log
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.http.HttpRequest
import com.apollographql.apollo3.api.http.HttpResponse
import com.apollographql.apollo3.network.http.HttpInterceptor
import com.apollographql.apollo3.network.http.HttpInterceptorChain
import com.apollographql.apollo3.network.http.LoggingInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import ru.andrewkir.data.BuildConfig
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
            .httpExposeErrorBody(true)
            .build()

}