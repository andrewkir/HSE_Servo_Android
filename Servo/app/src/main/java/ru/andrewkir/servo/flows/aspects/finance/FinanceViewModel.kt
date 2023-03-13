package ru.andrewkir.servo.flows.aspects.finance

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.andrewkir.domain.model.ApiResponse
import ru.andrewkir.domain.model.FinanceCategoryEnum
import ru.andrewkir.domain.model.FinanceModel
import ru.andrewkir.domain.model.FinanceObject
import ru.andrewkir.domain.repositories.FinanceRepository
import ru.andrewkir.domain.type.FinancialOperation
import ru.andrewkir.servo.common.BaseViewModel
import javax.inject.Inject

class FinanceViewModel @Inject constructor(
    val financeRepository: FinanceRepository
) : BaseViewModel() {

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