package ru.andrewkir.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StepsModel(
    val stepsList: List<StepsObject> = emptyList()
) : Parcelable

@Parcelize
data class StepsObject(
    var steps: Int,
    var date: String
): Parcelable