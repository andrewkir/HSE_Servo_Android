package ru.andrewkir.domain.repositories

import com.apollographql.apollo3.api.ApolloResponse
import ru.andrewkir.domain.CreateEmotionalRecordMutation
import ru.andrewkir.domain.GetEmotionsQuery
import ru.andrewkir.domain.RemoveEmotionMutation
import ru.andrewkir.domain.model.ApiResponse
import ru.andrewkir.domain.model.EmotionsModel

interface EmotionsRepository {
    suspend fun getData(): ApiResponse<ApolloResponse<GetEmotionsQuery.Data>>

    suspend fun addEmotion(emotionsModel: EmotionsModel): ApiResponse<ApolloResponse<CreateEmotionalRecordMutation.Data>>

    suspend fun removeEmotion(id: String): ApiResponse<ApolloResponse<RemoveEmotionMutation.Data>>
}