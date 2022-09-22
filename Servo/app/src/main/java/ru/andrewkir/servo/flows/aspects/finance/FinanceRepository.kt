package ru.andrewkir.servo.flows.aspects.finance

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import ru.andrewkir.servo.common.BaseRepository
import ru.andrewkir.servo.flows.aspects.finance.models.FinanceCategoryEnum
import ru.andrewkir.servo.flows.aspects.finance.models.FinanceModel
import ru.andrewkir.servo.flows.aspects.finance.models.FinanceObject
import java.util.*

class FinanceRepository(
) : BaseRepository() {

    fun getData(): MutableStateFlow<FinanceModel> {
        return MutableStateFlow(
            FinanceModel(
                arrayListOf(
                    FinanceObject(
                        "Долг",
                        10000.toDouble(),
                        Calendar.getInstance().time,
                        FinanceCategoryEnum.UNOFFICIAL_LOAN
                    ),
                    FinanceObject(
                        "Долг",
                        10000.toDouble(),
                        Calendar.getInstance().time,
                        FinanceCategoryEnum.UNOFFICIAL_LOAN
                    ),
                    FinanceObject(
                        "Долг в банке",
                        10000.toDouble(),
                        Calendar.getInstance().time,
                        FinanceCategoryEnum.BANK_LOAN
                    )
                )
            )
        )
    }
}