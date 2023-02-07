package ru.andrewkir.domain.repositories

import com.apollographql.apollo3.api.ApolloResponse
import ru.andrewkir.domain.CreateFinancialRecordMutation
import ru.andrewkir.domain.DeleteFinancialRecordMutation
import ru.andrewkir.domain.GetFinancialRecordQuery
import ru.andrewkir.domain.model.ApiResponse
import ru.andrewkir.domain.model.FinanceObject

interface FinanceRepository {
    suspend fun getData(): ApiResponse<ApolloResponse<GetFinancialRecordQuery.Data>>

    suspend fun addFinancialRecord(financeObject: FinanceObject): ApiResponse<ApolloResponse<CreateFinancialRecordMutation.Data>>

    suspend fun deleteFinancialRecord(data: String): ApiResponse<ApolloResponse<DeleteFinancialRecordMutation.Data>>
}