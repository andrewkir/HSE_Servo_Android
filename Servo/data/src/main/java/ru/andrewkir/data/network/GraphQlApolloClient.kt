package ru.andrewkir.data.network

import android.content.Context
import com.apollographql.apollo3.ApolloCall
import com.apollographql.apollo3.ApolloClient
import ru.andrewkir.data.network.common.BaseApolloClient
import ru.andrewkir.domain.*
import ru.andrewkir.domain.type.*

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

    fun getSteps(): ApolloCall<StepsActivityRecordsQuery.Data> {
        return apolloClient.query(StepsActivityRecordsQuery())
    }

    fun refreshSession(data: String): ApolloCall<RefreshSessionMutation.Data> {
        return apolloClient.mutation(RefreshSessionMutation(data))
    }

    fun addFinancialRecord(data: FinancialRecordCreateInput): ApolloCall<CreateFinancialRecordMutation.Data> {
        return apolloClient.mutation(CreateFinancialRecordMutation(data))
    }

    fun getFinancialRecords(): ApolloCall<GetFinancialRecordQuery.Data> {
        return apolloClient.query(GetFinancialRecordQuery())
    }

    fun deleteFinancialRecord(data: String): ApolloCall<DeleteFinancialRecordMutation.Data> {
        return apolloClient.mutation(DeleteFinancialRecordMutation(data))
    }

    fun addEmotionRecord(data: EmotionalStateRecordCreateInput): ApolloCall<CreateEmotionalRecordMutation.Data> {
        return apolloClient.mutation(CreateEmotionalRecordMutation(data))
    }

    fun getEmotions(): ApolloCall<GetEmotionsQuery.Data> {
        return apolloClient.query(GetEmotionsQuery())
    }

    fun removeEmotion(data: String): ApolloCall<RemoveEmotionMutation.Data> {
        return apolloClient.mutation(RemoveEmotionMutation(data))
    }
}
