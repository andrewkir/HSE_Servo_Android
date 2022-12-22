package ru.andrewkir.servo.flows.aspects.emotions

import ru.andrewkir.servo.common.BaseViewModel
import ru.andrewkir.servo.flows.aspects.emotions.models.EmotionsModel
import java.util.Date
import javax.inject.Inject

class EmotionsViewModel @Inject constructor(
    private val emotionsRepository: EmotionsRepository
) : BaseViewModel(emotionsRepository) {

    fun addEmotion(emotion: EmotionsModel, date: Date){

    }
}