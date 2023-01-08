package ru.andrewkir.servo.flows.aspects.steps

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.andrewkir.servo.common.BaseViewModel
import ru.andrewkir.servo.flows.aspects.finance.FinanceRepository
import ru.andrewkir.servo.flows.aspects.finance.models.FinanceModel
import ru.andrewkir.servo.flows.aspects.finance.models.FinanceObject
import ru.andrewkir.servo.flows.aspects.steps.models.StepsModel
import ru.andrewkir.servo.flows.aspects.steps.models.StepsObject
import java.util.Date
import javax.inject.Inject

class StepsViewModel @Inject constructor(
    val stepsRepository: StepsRepository
) : BaseViewModel(stepsRepository) {

    var mStepsData: MutableList<StepsObject> = mutableListOf()

    private val _stepsData = MutableStateFlow(StepsModel())
    val stepsData: StateFlow<StepsModel> = _stepsData

    fun getData() {
        viewModelScope.launch {
            stepsRepository.getData().collect{
                mStepsData = it.stepsList as MutableList<StepsObject>
                _stepsData.value = StepsModel(mStepsData)
            }
        }
    }

    fun addSteps(stepsObject: StepsObject){
        viewModelScope.launch {
            val items = _stepsData.value.copy().stepsList as MutableList
            val newItems: MutableList<StepsObject> = items.map { it.copy() } as MutableList<StepsObject>
            newItems.add(stepsObject)
            _stepsData.value = _stepsData.value.copy(stepsList = newItems)
            stepsRepository.addSteps(stepsObject)
        }
    }

    init {
        getData()
    }
}