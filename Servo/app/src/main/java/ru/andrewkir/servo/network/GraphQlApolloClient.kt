package ru.andrewkir.servo.network

import android.content.Context
import com.apollographql.apollo3.ApolloCall
import com.apollographql.apollo3.ApolloClient
import ru.andrewkir.SignupUserMutation
import ru.andrewkir.servo.network.common.BaseApolloClient
import ru.andrewkir.type.UserCreateInput

class ApolloProvider(context: Context) : BaseApolloClient() {
    private var apolloClient: ApolloClient = apolloClient(context)

    fun signUpUser(data: UserCreateInput): ApolloCall<SignupUserMutation.Data> {
        return apolloClient.mutation(SignupUserMutation(data))
    }
}
