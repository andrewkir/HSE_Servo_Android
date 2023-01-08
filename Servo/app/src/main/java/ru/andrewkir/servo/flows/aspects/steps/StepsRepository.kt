package ru.andrewkir.servo.flows.aspects.steps

import com.apollographql.apollo3.api.Optional
import kotlinx.coroutines.flow.MutableStateFlow
import ru.andrewkir.servo.common.BaseRepository
import ru.andrewkir.servo.flows.aspects.steps.models.StepsModel
import ru.andrewkir.servo.flows.aspects.steps.models.StepsObject
import ru.andrewkir.servo.network.ApolloProvider
import ru.andrewkir.type.StepsActivityRecordCreateInput
import java.util.*

class StepsRepository(
    private val api: ApolloProvider
) : BaseRepository() {
    fun getData(): MutableStateFlow<StepsModel> {
        return MutableStateFlow(
            StepsModel(
                arrayListOf(
                    StepsObject(1200, Date().toString()),
                    StepsObject(10000, Date().toString()),
                    StepsObject(7000, Date().toString()),
                    StepsObject(1200, Date().toString()),
                    StepsObject(10000, Date().toString()),
                    StepsObject(7000, Date().toString())
                )
            )
        )
    }

    fun addSteps(stepsObject: StepsObject){
        api.addSteps(StepsActivityRecordCreateInput(date = Optional.present(stepsObject.date), stepsCount = stepsObject.steps))
    }
}