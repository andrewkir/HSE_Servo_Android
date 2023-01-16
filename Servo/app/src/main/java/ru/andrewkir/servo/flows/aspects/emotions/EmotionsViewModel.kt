package ru.andrewkir.servo.flows.aspects.emotions

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.andrewkir.servo.common.BaseViewModel
import ru.andrewkir.servo.flows.aspects.emotions.models.Emotions
import ru.andrewkir.servo.flows.aspects.emotions.models.EmotionsModel
import ru.andrewkir.servo.flows.aspects.finance.models.FinanceCategoryEnum
import ru.andrewkir.servo.flows.aspects.finance.models.FinanceModel
import ru.andrewkir.servo.flows.aspects.finance.models.FinanceObject
import ru.andrewkir.servo.network.common.ApiResponse
import ru.andrewkir.type.EmotionalState
import ru.andrewkir.type.FinancialOperation
import java.util.Date
import javax.inject.Inject

class EmotionsViewModel @Inject constructor(
    private val emotionsRepository: EmotionsRepository
) : BaseViewModel(emotionsRepository) {
    var mEmotionsData: MutableList<EmotionsModel> = mutableListOf()

    private val _emotionsData = MutableStateFlow(listOf(EmotionsModel()))
    val emotionsData: StateFlow<List<EmotionsModel>> = _emotionsData

    fun getData() {
        viewModelScope.launch {
            mutableLoading.value = true
            when (val result = emotionsRepository.getData()) {
                is ApiResponse.OnSuccessResponse -> {
                    val res = result.value.data?.emotionalStateRecords?.map { it ->
                        EmotionsModel(
                            it.id,
                            if (it.state == EmotionalState.HAPPY) Emotions.HAPPY else if (it.state == EmotionalState.NORMAL) Emotions.NORMAL else Emotions.SAD,
                            it.description,
                            it.date.toString()
                        )
                    }
                    mEmotionsData = res!!.toMutableList()
                    _emotionsData.value = mEmotionsData
                }
                is ApiResponse.OnErrorResponse -> {
                    errorResponse.value = result
                }
            }
            mutableLoading.value = false
        }
    }

    fun addEmotion(emotion: EmotionsModel) {
        viewModelScope.launch {
            mEmotionsData.add(emotion)
            _emotionsData.value = mEmotionsData

            emotionsRepository.addEmotion(emotion)
            getData()
        }
    }

    fun removeEmotion(emotion: EmotionsModel) {
        viewModelScope.launch {
            mEmotionsData.remove(emotion)
            _emotionsData.value = mEmotionsData

            emotionsRepository.removeEmotion(emotion.id ?: "")
            getData()
        }
    }

    init {
        getData()
    }
}