package ru.andrewkir.servo.flows.aspects.emotions.models

import java.util.UUID

data class EmotionsModel(
    val id: String? = UUID.randomUUID().toString(),
    val emotion: Emotions? = null,
    val comment: String? = null,
    val date: String? = null
)

enum class Emotions {
    HAPPY,
    NORMAL,
    SAD
}