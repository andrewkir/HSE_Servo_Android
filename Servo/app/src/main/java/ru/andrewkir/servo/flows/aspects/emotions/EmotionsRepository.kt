package ru.andrewkir.servo.flows.aspects.emotions

import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Optional
import kotlinx.coroutines.flow.MutableStateFlow
import ru.andrewkir.CreateEmotionalRecordMutation
import ru.andrewkir.GetEmotionsQuery
import ru.andrewkir.GetFinancialRecordQuery
import ru.andrewkir.servo.common.BaseRepository
import ru.andrewkir.servo.flows.aspects.emotions.models.Emotions
import ru.andrewkir.servo.flows.aspects.emotions.models.EmotionsModel
import ru.andrewkir.servo.network.ApolloProvider
import ru.andrewkir.servo.network.common.ApiResponse
import ru.andrewkir.type.EmotionalState
import ru.andrewkir.type.EmotionalStateRecordCreateInput
import java.util.UUID

class EmotionsRepository(
    private val api: ApolloProvider
) : BaseRepository() {

    suspend fun getData(): ApiResponse<ApolloResponse<GetEmotionsQuery.Data>> {
        return protectedApiCall(
            api.getEmotions()
        )
    }

    suspend fun addEmotion(emotionsModel: EmotionsModel): ApiResponse<ApolloResponse<CreateEmotionalRecordMutation.Data>> {
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
}