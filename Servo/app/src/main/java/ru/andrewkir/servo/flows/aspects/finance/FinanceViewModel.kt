package ru.andrewkir.servo.flows.aspects.finance

import android.util.Log
import ru.andrewkir.servo.common.BaseViewModel
import javax.inject.Inject

class FinanceViewModel @Inject constructor(
    val financeRepository: FinanceRepository
) : BaseViewModel(financeRepository) {

    fun getData(): ArrayList<FinanceObject> {
        return financeRepository.getData()
    }
}