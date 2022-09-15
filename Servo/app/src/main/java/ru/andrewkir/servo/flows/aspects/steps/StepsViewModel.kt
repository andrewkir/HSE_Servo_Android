package ru.andrewkir.servo.flows.aspects.steps

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.andrewkir.servo.common.BaseViewModel
import ru.andrewkir.servo.flows.aspects.finance.FinanceRepository
import ru.andrewkir.servo.flows.aspects.finance.models.FinanceModel
import ru.andrewkir.servo.flows.aspects.finance.models.FinanceObject
import ru.andrewkir.servo.flows.aspects.steps.models.StepsModel
import ru.andrewkir.servo.flows.aspects.steps.models.StepsObject
import javax.inject.Inject

class StepsViewModel @Inject constructor(
    val stepsRepository: StepsRepository
) : BaseViewModel(stepsRepository) {

    var mStepsData: MutableList<StepsObject> = mutableListOf()

    private val _stepsData = MutableStateFlow(StepsModel())
    val stepsData: StateFlow<StepsModel> = _stepsData

    fun getData() {
        viewModelScope.launch {
            val result = stepsRepository.getData()
            mStepsData = result as MutableList<StepsObject>
            _stepsData.emit(StepsModel(mStepsData))
        }
    }

    fun addSteps(stepsObject: StepsObject){
        viewModelScope.launch {
            mStepsData.add(stepsObject)
            _stepsData.value = StepsModel(mStepsData)
        }
    }

    init {
        getData()
    }
}