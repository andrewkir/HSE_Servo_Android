package ru.andrewkir.servo.flows.main.dashboard.models

import ru.andrewkir.domain.model.StepsModel

data class StepsEntry(
    var data: StepsModel
) : DashboardModel{
    override val title = "Шаги"
    override val type = DashboardViews.StepsView
}