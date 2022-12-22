package ru.andrewkir.servo.flows.main.dashboard.models

interface DashboardModel {
    val title: String
    val type: DashboardViews
}

enum class DashboardViews{
    FinanceView,
    StepsView,
    EmotionsView
}