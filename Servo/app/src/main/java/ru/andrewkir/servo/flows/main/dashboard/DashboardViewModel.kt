package ru.andrewkir.servo.flows.main.dashboard

//import ru.andrewkir.hse_mooc.network.responses.ApiResponse
//import ru.andrewkir.hse_mooc.network.responses.Categories.CategoriesResponse
//import ru.andrewkir.hse_mooc.network.responses.CoursesPreview.CoursePreview
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ru.andrewkir.domain.model.*
import ru.andrewkir.domain.repositories.EmotionsRepository
import ru.andrewkir.domain.repositories.FinanceRepository
import ru.andrewkir.domain.repositories.StepsRepository
import ru.andrewkir.domain.type.EmotionalState
import ru.andrewkir.domain.type.FinancialOperation
import ru.andrewkir.servo.common.BaseViewModel
import ru.andrewkir.servo.flows.main.dashboard.models.DashboardModel
import ru.andrewkir.servo.flows.main.dashboard.models.EmotionsEntry
import ru.andrewkir.servo.flows.main.dashboard.models.FinanceEntry
import ru.andrewkir.servo.flows.main.dashboard.models.StepsEntry
import java.util.*
import javax.inject.Inject


class DashboardViewModel @Inject constructor(
    private val financeRepository: FinanceRepository,
    private val stepsRepository: StepsRepository,
    private val emotionsRepository: EmotionsRepository
) : BaseViewModel() {

    val financeFlow: MutableStateFlow<FinanceModel> by lazy { MutableStateFlow(FinanceModel()) }

    val stepsFlow: MutableStateFlow<StepsModel> by lazy { MutableStateFlow(StepsModel()) }

    val emotionsFlow: MutableStateFlow<List<EmotionsModel>> by lazy {
        MutableStateFlow(
            listOf()
        )
    }

    private var cardsData = mutableListOf(
        FinanceEntry(FinanceModel()),
        StepsEntry(StepsModel()),
        EmotionsEntry(listOf(EmotionsModel(UUID.randomUUID().toString(), Emotions.HAPPY, "", "")))
    )

    fun getData() {
        viewModelScope.launch {
            when (val result = financeRepository.getData()) {
                is ApiResponse.OnSuccessResponse -> {
                    val res = result.value.data?.financialRecords?.map { it ->
                        FinanceObject(
                            it.id,
                            it.title,
                            it.amount,
                            it.date.toString(),
                            if (it.type == FinancialOperation.DEBT) FinanceCategoryEnum.BANK_LOAN else FinanceCategoryEnum.GIVE_LOAN
                        )
                    }
                    financeFlow.emit(FinanceModel(res!!.toMutableList()))
                }
                is ApiResponse.OnErrorResponse -> {
                    errorResponse.value = result
                }
            }
        }
    }


    fun getStepsData() {
        viewModelScope.launch {
            when (val result = stepsRepository.getSteps()) {
                is ApiResponse.OnSuccessResponse -> {
                    stepsFlow.emit(StepsModel(result.value.data!!.stepsActivityRecords.map { it ->
                        StepsObject(
                            it.stepsCount,
                            it.date.toString()
                        )
                    }))
                }
                is ApiResponse.OnErrorResponse -> {
                    errorResponse.value = result
                }
            }
        }
    }

    fun getEmotionsData() {
        viewModelScope.launch {
            when (val result = emotionsRepository.getData()) {
                is ApiResponse.OnSuccessResponse -> {
                    result.value.data?.emotionalStateRecords?.map { it ->
                        EmotionsModel(
                            it.id,
                            if (it.state == EmotionalState.HAPPY) Emotions.HAPPY else if (it.state == EmotionalState.NORMAL) Emotions.NORMAL else Emotions.SAD,
                            it.description,
                            it.date.toString()
                        )
                    }?.let { it1 -> emotionsFlow.emit(it1) }
                }
                is ApiResponse.OnErrorResponse -> {
                    errorResponse.value = result
                }
            }
        }
    }

    fun getCardData(): MutableList<DashboardModel> {
        return cardsData
    }

    fun saveCardData(cardData: MutableList<DashboardModel>) {
        cardsData = cardData
    }
}