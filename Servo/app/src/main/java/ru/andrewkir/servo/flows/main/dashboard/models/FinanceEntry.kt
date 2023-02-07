package ru.andrewkir.servo.flows.main.dashboard.models

import ru.andrewkir.domain.model.FinanceModel

data class FinanceEntry(
    var data: FinanceModel
) : DashboardModel{
    override val title = "Финансы"
    override val type = DashboardViews.FinanceView
}