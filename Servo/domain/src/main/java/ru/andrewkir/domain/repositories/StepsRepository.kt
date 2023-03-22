package ru.andrewkir.domain.repositories

import com.apollographql.apollo3.api.ApolloResponse
import ru.andrewkir.domain.CreateStepsActivityRecordMutation
import ru.andrewkir.domain.StepsActivityRecordsQuery
import ru.andrewkir.domain.model.ApiResponse
import ru.andrewkir.domain.model.StepsObject

interface StepsRepository {
    suspend fun addSteps(stepsObject: StepsObject): ApiResponse<ApolloResponse<CreateStepsActivityRecordMutation.Data>>

    suspend fun getSteps(): ApiResponse<ApolloResponse<StepsActivityRecordsQuery.Data>>
}