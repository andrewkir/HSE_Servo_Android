package ru.andrewkir.servo.flows.aspects.finance

import ru.andrewkir.servo.common.BaseViewModel
import ru.andrewkir.servo.flows.aspects.finance.models.FinanceObject
import javax.inject.Inject

class FinanceViewModel @Inject constructor(
    val financeRepository: FinanceRepository
) : BaseViewModel(financeRepository) {

    fun getData(): ArrayList<FinanceObject> {
        return financeRepository.getData()
    }
}