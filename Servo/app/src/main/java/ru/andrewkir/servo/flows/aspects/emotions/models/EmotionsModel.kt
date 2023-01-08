package ru.andrewkir.servo.flows.aspects.emotions.models

data class EmotionsModel(
    val emotion: Emotions?,
    val comment: String?
)

enum class Emotions{
    HAPPY,
    NORMAL,
    SAD
}