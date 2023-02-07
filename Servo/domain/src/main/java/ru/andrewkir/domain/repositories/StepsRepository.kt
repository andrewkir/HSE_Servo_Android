package ru.andrewkir.domain.repositories

import com.apollographql.apollo3.api.ApolloResponse
import kotlinx.coroutines.flow.MutableStateFlow
import ru.andrewkir.domain.StepsActivityRecordsQuery
import ru.andrewkir.domain.model.ApiResponse
import ru.andrewkir.domain.model.StepsModel
import ru.andrewkir.domain.model.StepsObject

interface StepsRepository {
    fun getData(): MutableStateFlow<StepsModel>

    fun addSteps(stepsObject: StepsObject)

    suspend fun getSteps(): ApiResponse<ApolloResponse<StepsActivityRecordsQuery.Data>>
}