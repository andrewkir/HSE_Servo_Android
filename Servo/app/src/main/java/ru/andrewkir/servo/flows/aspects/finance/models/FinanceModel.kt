package ru.andrewkir.servo.flows.aspects.finance.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*
import kotlin.collections.ArrayList

@Parcelize
data class FinanceModel(
    val financeList: List<FinanceObject> = emptyList()
) : Parcelable

@Parcelize
data class FinanceObject(
    var name: String? = null,
    var sum: Double? = null,
    var date: Date? = null,
    var category: FinanceCategoryEnum? = null
): Parcelable

enum class FinanceCategoryEnum{
    BANK_LOAN,
    UNOFFICIAL_LOAN,
    GIVE_LOAN
}