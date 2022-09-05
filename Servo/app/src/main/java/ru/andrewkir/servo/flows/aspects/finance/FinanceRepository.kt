package ru.andrewkir.servo.flows.aspects.finance

import ru.andrewkir.servo.common.BaseRepository
import ru.andrewkir.servo.flows.aspects.finance.models.FinanceCategoryEnum
import ru.andrewkir.servo.flows.aspects.finance.models.FinanceObject

class FinanceRepository(
    ) : BaseRepository() {
    fun getData(): ArrayList<FinanceObject> {
        return arrayListOf(
            FinanceObject("Долг", 10000.toDouble(), null, FinanceCategoryEnum.UNOFFICIAL_LOAN),
            FinanceObject("Долг", 10000.toDouble(), null, FinanceCategoryEnum.UNOFFICIAL_LOAN),
            FinanceObject("Долг в банке", 10000.toDouble(), null, FinanceCategoryEnum.BANK_LOAN)
        )
    }
}