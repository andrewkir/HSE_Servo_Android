package ru.andrewkir.servo.flows.aspects.steps.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class StepsModel(
    val stepsList: List<StepsObject> = emptyList()
) : Parcelable

@Parcelize
data class StepsObject(
    var steps: Int,
    var date: String
): Parcelable