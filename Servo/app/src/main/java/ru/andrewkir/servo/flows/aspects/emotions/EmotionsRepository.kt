package ru.andrewkir.servo.flows.aspects.emotions

import kotlinx.coroutines.flow.MutableStateFlow
import ru.andrewkir.servo.common.BaseRepository
import ru.andrewkir.servo.flows.aspects.emotions.models.Emotions
import ru.andrewkir.servo.flows.aspects.emotions.models.EmotionsModel

class EmotionsRepository : BaseRepository() {
    fun getData(): MutableStateFlow<List<EmotionsModel>> {
        return MutableStateFlow(
            listOf(
                EmotionsModel(
                    Emotions.HAPPY,
                    "happy"
                )
            )
        )
    }
}