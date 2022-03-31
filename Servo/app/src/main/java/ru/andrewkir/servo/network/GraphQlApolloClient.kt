package ru.andrewkir.servo.network

import android.content.Context
import com.apollographql.apollo3.ApolloCall
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Input
import com.apollographql.apollo3.exception.ApolloException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.andrewkir.GetCharactersQuery
import ru.andrewkir.servo.network.common.ApiResponse
import ru.andrewkir.servo.network.common.BASE_URL
import ru.andrewkir.servo.network.common.BaseApolloClient
import java.net.SocketTimeoutException

class RickAndMortyClient(context: Context) : BaseApolloClient() {
    private var apolloClient: ApolloClient = apolloClient(context)

//    fun  getCharacters(page: Int): ApolloCall<GetCharactersQuery.Data> {
//        return apolloClient.query(GetCharactersQuery(Input.optional(page)))
//    }
}
