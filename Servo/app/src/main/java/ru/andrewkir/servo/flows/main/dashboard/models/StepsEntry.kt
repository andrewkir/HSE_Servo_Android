package ru.andrewkir.servo.flows.main.dashboard.models

import ru.andrewkir.servo.flows.aspects.finance.models.FinanceModel
import ru.andrewkir.servo.flows.aspects.steps.models.StepsModel

data class StepsEntry(
    var data: StepsModel
) : DashboardModel{
    override val title = "Шаги"
    override val type = DashboardViews.StepsView
}