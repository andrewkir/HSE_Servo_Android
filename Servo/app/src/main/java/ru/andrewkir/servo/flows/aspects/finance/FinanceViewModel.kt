package ru.andrewkir.servo.flows.aspects.finance

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.andrewkir.servo.common.BaseViewModel
import ru.andrewkir.servo.flows.aspects.finance.models.FinanceCategoryEnum
import ru.andrewkir.servo.flows.aspects.finance.models.FinanceModel
import ru.andrewkir.servo.flows.aspects.finance.models.FinanceObject
import javax.inject.Inject

class FinanceViewModel @Inject constructor(
    val financeRepository: FinanceRepository
) : BaseViewModel(financeRepository) {

    var mFinanceData: MutableList<FinanceObject> = mutableListOf()

    private val _financeData = MutableStateFlow(FinanceModel())
    val financeData: StateFlow<FinanceModel> = _financeData

    fun getData() {
        viewModelScope.launch {
            val result = financeRepository.getData()
            mFinanceData = result as MutableList<FinanceObject>
            _financeData.emit(FinanceModel(mFinanceData))
        }
    }

    fun removeLoan(financeObject: FinanceObject){
        viewModelScope.launch {
            mFinanceData.remove(financeObject)
            _financeData.value = FinanceModel(mFinanceData)
        }
    }

    fun addLoan(financeObject: FinanceObject) {
        viewModelScope.launch {
            mFinanceData.add(financeObject)
            _financeData.value = FinanceModel(mFinanceData)
        }
    }

    init {
        getData()
    }
}