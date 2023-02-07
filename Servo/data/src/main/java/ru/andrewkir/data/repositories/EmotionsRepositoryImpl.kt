package ru.andrewkir.data.repositories

import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Optional
import ru.andrewkir.data.network.ApolloProvider
import ru.andrewkir.domain.CreateEmotionalRecordMutation
import ru.andrewkir.domain.GetEmotionsQuery
import ru.andrewkir.domain.RemoveEmotionMutation
import ru.andrewkir.domain.model.ApiResponse
import ru.andrewkir.domain.model.Emotions
import ru.andrewkir.domain.model.EmotionsModel
import ru.andrewkir.domain.repositories.BaseRepository
import ru.andrewkir.domain.repositories.EmotionsRepository
import ru.andrewkir.domain.type.EmotionalState
import ru.andrewkir.domain.type.EmotionalStateRecordCreateInput

class EmotionsRepositoryImpl(private val api: ApolloProvider) : EmotionsRepository,
    BaseRepository() {
    override suspend fun getData(): ApiResponse<ApolloResponse<GetEmotionsQuery.Data>> {
        return protectedApiCall(
            api.getEmotions()
        )
    }

    override suspend fun addEmotion(emotionsModel: EmotionsModel): ApiResponse<ApolloResponse<CreateEmotionalRecordMutation.Data>> {
        return protectedApiCall(
            api.addEmotionRecord(
                EmotionalStateRecordCreateInput(
                    description = Optional.presentIfNotNull(emotionsModel.comment),
                    state = if (emotionsModel.emotion == Emotions.HAPPY) EmotionalState.HAPPY else if (emotionsModel.emotion == Emotions.NORMAL) EmotionalState.NORMAL else EmotionalState.SAD,
                    date = emotionsModel.date.toString()
                )
            )
        )
    }

    override suspend fun removeEmotion(id: String): ApiResponse<ApolloResponse<RemoveEmotionMutation.Data>> {
        return protectedApiCall(
            api.removeEmotion(id)
        )
    }
}