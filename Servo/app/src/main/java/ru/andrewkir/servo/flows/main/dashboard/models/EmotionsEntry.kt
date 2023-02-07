package ru.andrewkir.servo.flows.main.dashboard.models

import ru.andrewkir.domain.model.EmotionsModel

data class EmotionsEntry(
    var data: List<EmotionsModel>?
) : DashboardModel{
    override val title = "Эмоции"
    override val type = DashboardViews.EmotionsView
}