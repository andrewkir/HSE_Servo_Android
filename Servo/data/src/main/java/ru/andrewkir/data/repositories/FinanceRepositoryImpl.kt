package ru.andrewkir.data.repositories

import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Optional
import ru.andrewkir.data.network.ApolloProvider
import ru.andrewkir.domain.CreateFinancialRecordMutation
import ru.andrewkir.domain.DeleteFinancialRecordMutation
import ru.andrewkir.domain.GetFinancialRecordQuery
import ru.andrewkir.domain.model.ApiResponse
import ru.andrewkir.domain.model.FinanceCategoryEnum
import ru.andrewkir.domain.model.FinanceObject
import ru.andrewkir.domain.repositories.BaseRepository
import ru.andrewkir.domain.repositories.FinanceRepository
import ru.andrewkir.domain.type.FinancialOperation
import ru.andrewkir.domain.type.FinancialRecordCreateInput

class FinanceRepositoryImpl(
    private val api: ApolloProvider
) : FinanceRepository, BaseRepository() {

    override suspend fun getData(): ApiResponse<ApolloResponse<GetFinancialRecordQuery.Data>> {
        return protectedApiCall(
            api.getFinancialRecords()
        )
    }

    override suspend fun addFinancialRecord(financeObject: FinanceObject): ApiResponse<ApolloResponse<CreateFinancialRecordMutation.Data>> {
        return protectedApiCall(
            api.addFinancialRecord(
                FinancialRecordCreateInput(
                    type = if (financeObject.category == FinanceCategoryEnum.BANK_LOAN || financeObject.category == FinanceCategoryEnum.GIVE_LOAN)
                        FinancialOperation.LOAN else FinancialOperation.DEBT,
                    amount = financeObject.sum ?: 0.0,
                    date = Optional.present(financeObject.date.toString()),
                    title = financeObject.name ?: ""
                )
            )
        )
    }

    override suspend fun deleteFinancialRecord(data: String): ApiResponse<ApolloResponse<DeleteFinancialRecordMutation.Data>> {
        return protectedApiCall(
            api.deleteFinancialRecord(data)
        )
    }
}