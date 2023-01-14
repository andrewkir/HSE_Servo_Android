package ru.andrewkir.servo.flows.aspects.finance

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.andrewkir.servo.common.BaseViewModel
import ru.andrewkir.servo.flows.aspects.finance.models.FinanceCategoryEnum
import ru.andrewkir.servo.flows.aspects.finance.models.FinanceModel
import ru.andrewkir.servo.flows.aspects.finance.models.FinanceObject
import ru.andrewkir.servo.flows.aspects.steps.models.StepsModel
import ru.andrewkir.servo.flows.aspects.steps.models.StepsObject
import ru.andrewkir.servo.network.common.ApiResponse
import ru.andrewkir.type.FinancialOperation
import javax.inject.Inject

class FinanceViewModel @Inject constructor(
    val financeRepository: FinanceRepository
) : BaseViewModel(financeRepository) {

    var mFinanceData: MutableList<FinanceObject> = mutableListOf()

    private val _financeData = MutableStateFlow(FinanceModel())
    val financeData: StateFlow<FinanceModel> = _financeData

    fun getData() {
        viewModelScope.launch {
            mutableLoading.value = true
            when (val result = financeRepository.getData()) {
                is ApiResponse.OnSuccessResponse -> {
                    val res = result.value.data?.financialRecords?.map { it ->
                        FinanceObject(
                            it.id,
                            it.title,
                            it.amount,
                            it.date.toString(),
                            if (it.type == FinancialOperation.DEBT) FinanceCategoryEnum.BANK_LOAN else FinanceCategoryEnum.GIVE_LOAN
                        )
                    }
                    mFinanceData = res!!.toMutableList()
                    _financeData.value = FinanceModel(mFinanceData)
                }
                is ApiResponse.OnErrorResponse -> {
                    errorResponse.value = result
                }
            }
            mutableLoading.value = false
        }
    }

    fun removeLoan(financeObject: FinanceObject) {
        viewModelScope.launch {
            mFinanceData.remove(financeObject)
            _financeData.value = FinanceModel(mFinanceData)

            financeRepository.deleteFinancialRecord(financeObject.id)
        }
    }

    fun addLoan(financeObject: FinanceObject) {
        viewModelScope.launch {
            mFinanceData.add(financeObject)
            _financeData.value = FinanceModel(mFinanceData)

            financeRepository.addFinancialRecord(financeObject)
        }
    }

    init {
        getData()
    }
}