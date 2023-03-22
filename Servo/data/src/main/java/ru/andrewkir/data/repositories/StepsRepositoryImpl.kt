package ru.andrewkir.data.repositories

import com.apollographql.apollo3.api.Optional
import com.apollographql.apollo3.api.Optional.Companion.presentIfNotNull
import kotlinx.coroutines.flow.MutableStateFlow
import ru.andrewkir.data.network.ApolloProvider
import ru.andrewkir.domain.model.StepsModel
import ru.andrewkir.domain.model.StepsObject
import ru.andrewkir.domain.repositories.BaseRepository
import ru.andrewkir.domain.repositories.StepsRepository
import ru.andrewkir.domain.type.StepsActivityRecordCreateInput

class StepsRepositoryImpl(
    private val api: ApolloProvider
) : StepsRepository, BaseRepository() {

    override suspend fun addSteps(stepsObject: StepsObject) = protectedApiCall(
        api.addSteps(
            StepsActivityRecordCreateInput(
                date = presentIfNotNull(stepsObject.date), stepsCount = stepsObject.steps
            )
        )
    )

    override suspend fun getSteps() = protectedApiCall(
        api.getSteps()
    )
}