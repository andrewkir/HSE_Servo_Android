package ru.andrewkir.servo.flows.main.dashboard

//import ru.andrewkir.hse_mooc.network.responses.ApiResponse
//import ru.andrewkir.hse_mooc.network.responses.Categories.CategoriesResponse
//import ru.andrewkir.hse_mooc.network.responses.CoursesPreview.CoursePreview
import android.util.Log
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ru.andrewkir.servo.common.BaseViewModel
import ru.andrewkir.servo.flows.aspects.emotions.EmotionsRepository
import ru.andrewkir.servo.flows.aspects.emotions.models.Emotions
import ru.andrewkir.servo.flows.aspects.emotions.models.EmotionsModel
import ru.andrewkir.servo.flows.aspects.finance.FinanceRepository
import ru.andrewkir.servo.flows.aspects.finance.models.FinanceCategoryEnum
import ru.andrewkir.servo.flows.aspects.finance.models.FinanceModel
import ru.andrewkir.servo.flows.aspects.finance.models.FinanceObject
import ru.andrewkir.servo.flows.aspects.steps.StepsRepository
import ru.andrewkir.servo.flows.aspects.steps.models.StepsModel
import ru.andrewkir.servo.flows.aspects.steps.models.StepsObject
import ru.andrewkir.servo.flows.main.dashboard.models.DashboardModel
import ru.andrewkir.servo.flows.main.dashboard.models.EmotionsEntry
import ru.andrewkir.servo.flows.main.dashboard.models.FinanceEntry
import ru.andrewkir.servo.flows.main.dashboard.models.StepsEntry
import ru.andrewkir.servo.network.common.ApiResponse
import ru.andrewkir.type.FinancialOperation
import javax.inject.Inject


class DashboardViewModel @Inject constructor(
    val dashboardRepository: DashboardRepository,
    private val financeRepository: FinanceRepository,
    private val stepsRepository: StepsRepository,
    private val emotionsRepository: EmotionsRepository
) : BaseViewModel(dashboardRepository) {

    val financeFlow: MutableStateFlow<FinanceModel> by lazy { MutableStateFlow(FinanceModel()) }

    val stepsFlow: MutableStateFlow<StepsModel> by lazy { MutableStateFlow(StepsModel()) }

    val emotionsFlow: MutableStateFlow<List<EmotionsModel>> by lazy {
        MutableStateFlow(
            listOf(
                EmotionsModel(
                    Emotions.HAPPY,
                    ""
                )
            )
        )
    }

    private var cardsData = mutableListOf(
        FinanceEntry(FinanceModel()),
        StepsEntry(StepsModel()),
        EmotionsEntry(listOf(EmotionsModel(Emotions.HAPPY, "")))
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
            emotionsRepository.getData().collect {
                emotionsFlow.emit(it)
            }
        }
    }

    fun getCardData(): MutableList<DashboardModel> {
        return cardsData
    }

    fun saveCardData(cardData: MutableList<DashboardModel>) {
        cardsData = cardData
    }

    //
//    private val mutableCourses = arrayListOf<CoursePreview>()
//    private val mutableCoursesLiveData: MutableLiveData<List<CoursePreview>> = MutableLiveData()
//    val coursesLiveData: LiveData<List<CoursePreview>>
//        get() = mutableCoursesLiveData
//
//    val error: MutableLiveData<ApiResponse.OnErrorResponse?> by lazy {
//        MutableLiveData<ApiResponse.OnErrorResponse?>()
//    }
//
//    val isLastPage: MutableLiveData<Boolean> by lazy {
//        MutableLiveData<Boolean>()
//    }
//
//    val categoryResponse: MutableLiveData<ApiResponse<CategoriesResponse>> = MutableLiveData()
//
    init {
        Log.d("VIEWMODEL", "INIT viewmodel")
    }
//
//    private var page = 1
//    private var query = ""
//    private var isFirstInit = true
//    var categories = mutableSetOf<Int>()
//
//    private fun fetchCourses(query: String, currentPage: Int) {
//        if (isLastPage.value!!) return
//
//        error.value = null
//        viewModelScope.launch {
//            mutableLoading.value = true
//            when (val result = searchRepository.getCoursesFromServer(
//                query,
//                currentPage,
//                categories.joinToString(",")
//            )) {
//                is ApiResponse.OnSuccessResponse -> {
//                    error.value = null
//
//                    if (result.value.courses.isEmpty()) {
//                        page--
//                        isLastPage.value = true
//                    }
//                    mutableCourses.addAll(result.value.courses)
//                    mutableCoursesLiveData.value = mutableCourses
//                }
//                is ApiResponse.OnErrorResponse -> {
//                    page--
//                    error.value = result
//                }
//            }
//            mutableLoading.value = false
//        }
//    }
//
//    fun initCourses() {
//        if (isFirstInit) {
//            isFirstInit = false
//            refreshCourses()
//            getCategories()
//        }
//    }
//
//    fun searchCourses(searchQuery: String, categoriesQuery: Set<Int> = setOf()) {
//        if (searchQuery != query || !(categoriesQuery.containsAll(categories) && categories.containsAll(
//                categoriesQuery
//            ))
//        ) {
//            query = searchQuery
//            categories = categoriesQuery.toMutableSet()
//
//            refreshCourses()
//        } else {
//            refreshCourses()
//        }
//    }
//
//    fun nextPage() {
//        page++
//        fetchCourses(query, page)
//    }
//
//    fun refreshCourses(categoriesQuery: Set<Int>? = null) {
//        if (categoriesQuery != null) categories = categoriesQuery.toMutableSet()
//
//        error.value = null
//        page = 1
//
//        viewModelScope.launch {
//            mutableLoading.value = true
//            when (val result =
//                searchRepository.getCoursesFromServer(query, 1, categories.joinToString(","))) {
//                is ApiResponse.OnSuccessResponse -> {
//                    mutableCourses.clear()
//                    isLastPage.value =
//                        result.value.courses.isEmpty() || result.value.courses.size < 30
//                    mutableCourses.addAll(result.value.courses)
//                    mutableCoursesLiveData.value = mutableCourses
//                }
//                is ApiResponse.OnErrorResponse -> {
//                    error.value = result
//                }
//            }
//            mutableLoading.value = false
//        }
//    }
//
//    fun getCategories() {
//        viewModelScope.launch {
//            categoryResponse.value = searchRepository.getCategories()
//        }
//    }
}