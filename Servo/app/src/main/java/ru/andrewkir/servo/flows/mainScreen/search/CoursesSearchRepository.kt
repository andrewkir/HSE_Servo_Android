package ru.andrewkir.hse_mooc.flows.courses.search

import ru.andrewkir.hse_mooc.common.BaseRepository
import ru.andrewkir.hse_mooc.network.api.CoursesApi
import ru.andrewkir.hse_mooc.network.requests.RegisterRequest

class CoursesSearchRepository(
    private val coursesApi: CoursesApi
) : BaseRepository() {

    suspend fun getCoursesFromServer(query: String, currentPage: Int, categories: String = "") = protectedApiCall {
        coursesApi.getCourses(30, currentPage, query, categories)
    }

    suspend fun getCategories() = protectedApiCall {
        coursesApi.getCategories()
    }
}