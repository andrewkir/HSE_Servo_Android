package ru.andrewkir.servo.flows.main.dashboard.models

import ru.andrewkir.servo.flows.aspects.emotions.models.EmotionsModel
import ru.andrewkir.servo.flows.aspects.finance.models.FinanceModel

data class EmotionsEntry(
    var data: List<EmotionsModel>?
) : DashboardModel{
    override val title = "Эмоции"
    override val type = DashboardViews.EmotionsView
}