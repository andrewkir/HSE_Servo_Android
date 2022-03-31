package ru.andrewkir.servo.common

import com.apollographql.apollo3.ApolloCall
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Mutation
import com.apollographql.apollo3.api.Operation
import com.apollographql.apollo3.api.Query
import com.apollographql.apollo3.exception.ApolloException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.andrewkir.servo.network.common.ApiResponse
import java.net.SocketTimeoutException

abstract class BaseRepository {

    suspend fun <T: ApolloCall<D>, D> protectedApiCall(
        apolloCall: T
    ): ApiResponse<ApolloResponse<D>>
        where D : Operation.Data
    {
        return withContext(Dispatchers.IO) {
            try {
                ApiResponse.OnSuccessResponse(apolloCall.execute())
            } catch (ex: Throwable) {
                when (ex) {
                    is ApolloException -> {
                        ApiResponse.OnErrorResponse(false, 200, ex.localizedMessage)
                    }
                    is SocketTimeoutException -> {
                        ApiResponse.OnErrorResponse(false, null, null)
                    }
                    else -> {
                        ApiResponse.OnErrorResponse(true, null, null)
                    }
                }
            }
        }
    }

//    suspend fun userLogout(api: AuthApi): ApiResponse<ResponseBody> {
//        return protectedApiCall { api.logout() }
//    }
}