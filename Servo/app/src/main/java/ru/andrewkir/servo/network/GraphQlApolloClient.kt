package ru.andrewkir.servo.network

import android.content.Context
import com.apollographql.apollo3.ApolloCall
import com.apollographql.apollo3.ApolloClient
import ru.andrewkir.CreateStepsActivityRecordMutation
import ru.andrewkir.SigninUserMutation
import ru.andrewkir.SignupUserMutation
import ru.andrewkir.StepsActivityRecordsQuery
import ru.andrewkir.servo.network.common.BaseApolloClient
import ru.andrewkir.type.StepsActivityRecord
import ru.andrewkir.type.StepsActivityRecordCreateInput
import ru.andrewkir.type.UserCreateInput
import ru.andrewkir.type.UserSigninInput

class ApolloProvider(context: Context) : BaseApolloClient() {
    private var apolloClient: ApolloClient = apolloClient(context)

    fun signUpUser(data: UserCreateInput): ApolloCall<SignupUserMutation.Data> {
        return apolloClient.mutation(SignupUserMutation(data))
    }

    fun signInUser(data: UserSigninInput): ApolloCall<SigninUserMutation.Data> {
        return apolloClient.mutation(SigninUserMutation(data))
    }

    fun addSteps(data: StepsActivityRecordCreateInput): ApolloCall<CreateStepsActivityRecordMutation.Data> {
        return apolloClient.mutation(CreateStepsActivityRecordMutation(data))
    }

    fun getSteps(data: StepsActivityRecord): ApolloCall<StepsActivityRecordsQuery.Data> {
        return apolloClient.query(StepsActivityRecordsQuery)
    }
}
