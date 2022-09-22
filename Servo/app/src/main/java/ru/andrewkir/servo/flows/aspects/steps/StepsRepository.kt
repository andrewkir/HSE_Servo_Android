package ru.andrewkir.servo.flows.aspects.steps

import kotlinx.coroutines.flow.MutableStateFlow
import ru.andrewkir.servo.common.BaseRepository
import ru.andrewkir.servo.flows.aspects.steps.models.StepsModel
import ru.andrewkir.servo.flows.aspects.steps.models.StepsObject
import java.util.*

class StepsRepository : BaseRepository() {
    fun getData(): MutableStateFlow<StepsModel> {
        return MutableStateFlow(
            StepsModel(
                arrayListOf(
                    StepsObject(1200, Date()),
                    StepsObject(10000, Date()),
                    StepsObject(7000, Date()),
                    StepsObject(1200, Date()),
                    StepsObject(10000, Date()),
                    StepsObject(7000, Date())
                )
            )
        )
    }
}