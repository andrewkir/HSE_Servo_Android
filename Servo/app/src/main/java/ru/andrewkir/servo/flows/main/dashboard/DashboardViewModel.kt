package ru.andrewkir.servo.flows.main.dashboard

import android.util.Log
//import ru.andrewkir.hse_mooc.network.responses.ApiResponse
//import ru.andrewkir.hse_mooc.network.responses.Categories.CategoriesResponse
//import ru.andrewkir.hse_mooc.network.responses.CoursesPreview.CoursePreview
import ru.andrewkir.servo.common.BaseViewModel
import ru.andrewkir.servo.flows.aspects.finance.*
import ru.andrewkir.servo.flows.aspects.finance.models.FinanceObject
import java.util.*
import javax.inject.Inject


class DashboardViewModel @Inject constructor(
    val dashboardRepository: DashboardRepository,
    private val financeRepository: FinanceRepository
) : BaseViewModel(dashboardRepository) {

    fun getData(): List<FinanceObject> = financeRepository.getData()

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
        Log.d("VIEWMODEL","INIT viewmodel")
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