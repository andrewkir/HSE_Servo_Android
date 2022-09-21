package ru.andrewkir.servo.flows.main.dashboard.models

import ru.andrewkir.servo.flows.aspects.finance.models.FinanceModel

data class FinanceEntry(
    var data: FinanceModel
) : DashboardModel{
    override val title = "Финансы"
    override val type = DashboardViews.FinanceView
}