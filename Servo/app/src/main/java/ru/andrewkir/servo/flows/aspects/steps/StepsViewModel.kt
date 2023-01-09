package ru.andrewkir.servo.flows.aspects.steps

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.api.Optional
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.andrewkir.SigninUserMutation
import ru.andrewkir.StepsActivityRecordsQuery
import ru.andrewkir.servo.common.BaseViewModel
import ru.andrewkir.servo.flows.aspects.finance.FinanceRepository
import ru.andrewkir.servo.flows.aspects.finance.models.FinanceModel
import ru.andrewkir.servo.flows.aspects.finance.models.FinanceObject
import ru.andrewkir.servo.flows.aspects.steps.models.StepsModel
import ru.andrewkir.servo.flows.aspects.steps.models.StepsObject
import ru.andrewkir.servo.network.common.ApiResponse
import java.util.Date
import javax.inject.Inject

class StepsViewModel @Inject constructor(
    val stepsRepository: StepsRepository
) : BaseViewModel(stepsRepository) {

    private val _stepsData = MutableStateFlow(StepsModel())
    val stepsData: StateFlow<StepsModel> = _stepsData

    private val mutableStepsResponse: MutableLiveData<StepsActivityRecordsQuery.Data> =
        MutableLiveData()

    val stepsResponse: LiveData<StepsActivityRecordsQuery.Data>
        get() = mutableStepsResponse

    fun getData() {
        viewModelScope.launch {
            mutableLoading.value = true
            when(val result = stepsRepository.getSteps()){
                is ApiResponse.OnSuccessResponse -> {
                    mutableStepsResponse.value = result.value.data!!
                }
                is ApiResponse.OnErrorResponse -> {
                    errorResponse.value = result
                }
            }
            mutableLoading.value = false
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