package ru.andrewkir.servo.flows.aspects.finance

import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Optional
import ru.andrewkir.CreateFinancialRecordMutation
import ru.andrewkir.DeleteFinancialRecordMutation
import ru.andrewkir.GetFinancialRecordQuery
import ru.andrewkir.servo.common.BaseRepository
import ru.andrewkir.servo.flows.aspects.finance.models.FinanceCategoryEnum
import ru.andrewkir.servo.flows.aspects.finance.models.FinanceObject
import ru.andrewkir.servo.network.ApolloProvider
import ru.andrewkir.servo.network.common.ApiResponse
import ru.andrewkir.type.FinancialOperation
import ru.andrewkir.type.FinancialRecordCreateInput

class FinanceRepository(
    private val api: ApolloProvider
) : BaseRepository() {

    suspend fun getData(): ApiResponse<ApolloResponse<GetFinancialRecordQuery.Data>> {
        return protectedApiCall(
            api.getFinancialRecords()
        )
    }

    suspend fun addFinancialRecord(financeObject: FinanceObject): ApiResponse<ApolloResponse<CreateFinancialRecordMutation.Data>> {
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

    suspend fun deleteFinancialRecord(data: String): ApiResponse<ApolloResponse<DeleteFinancialRecordMutation.Data>> {
        return protectedApiCall(
            api.deleteFinancialRecord(data)
        )
    }
}