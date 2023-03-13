package ru.andrewkir.servo.flows.aspects.steps

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.andrewkir.domain.StepsActivityRecordsQuery
import ru.andrewkir.domain.model.ApiResponse
import ru.andrewkir.domain.model.StepsModel
import ru.andrewkir.domain.model.StepsObject
import ru.andrewkir.domain.repositories.StepsRepository
import ru.andrewkir.servo.common.BaseViewModel
import javax.inject.Inject

class StepsViewModel @Inject constructor(
    val stepsRepository: StepsRepository
) : BaseViewModel() {

    private val _stepsData = MutableStateFlow(StepsModel())
    val stepsData: StateFlow<StepsModel> = _stepsData

    private val mutableStepsResponse: MutableLiveData<StepsActivityRecordsQuery.Data> =
        MutableLiveData()

    val stepsResponse: LiveData<StepsActivityRecordsQuery.Data>
        get() = mutableStepsResponse

    private fun getData() {
        viewModelScope.launch {
            mutableLoading.value = true
            when (val result = stepsRepository.getSteps()) {
                is ApiResponse.OnSuccessResponse -> {
                    mutableStepsResponse.value = result.value.data!!
                    _stepsData.value =
                        StepsModel(result.value.data!!.stepsActivityRecords.map { it ->
                            StepsObject(
                                it.stepsCount,
                                it.date.toString()
                            )
                        })
                }
                is ApiResponse.OnErrorResponse -> {
                    errorResponse.value = result
                }
            }
            mutableLoading.value = false
        }
    }

    fun addSteps(stepsObject: StepsObject) {
        viewModelScope.launch {
            var items: MutableList<StepsObject> = mutableListOf()
            if (_stepsData.value.copy().stepsList.isNotEmpty()) {
                items = _stepsData.value.copy().stepsList as MutableList
            }
            val newItems: MutableList<StepsObject> =
                items.map { it.copy() } as MutableList<StepsObject>
            newItems.add(stepsObject)
            _stepsData.value = _stepsData.value.copy(stepsList = newItems)
            stepsRepository.addSteps(stepsObject)
        }
    }

    init {
        getData()
    }
}