package ru.andrewkir.servo.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.andrewkir.data.network.ApolloProvider
import ru.andrewkir.data.repositories.AuthRepositoryImpl
import ru.andrewkir.data.repositories.EmotionsRepositoryImpl
import ru.andrewkir.data.repositories.FinanceRepositoryImpl
import ru.andrewkir.data.repositories.StepsRepositoryImpl
import ru.andrewkir.domain.repositories.AuthRepository
import ru.andrewkir.domain.repositories.EmotionsRepository
import ru.andrewkir.domain.repositories.FinanceRepository
import ru.andrewkir.domain.repositories.StepsRepository
import ru.andrewkir.servo.flows.main.dashboard.DashboardRepository
import ru.andrewkir.servo.flows.main.profile.ProfileRepository

@Module
class DataModule {
    @Provides
    fun provideFinanceRepository(context: Context): FinanceRepository =
        FinanceRepositoryImpl(ApolloProvider(context))

    @Provides
    fun provideAuthRepository(context: Context): AuthRepository =
        AuthRepositoryImpl(ApolloProvider(context))

    @Provides
    fun provideProfileRepository(): ProfileRepository = ProfileRepository()

    @Provides
    fun provideDashBoardRepository(): DashboardRepository = DashboardRepository()

    @Provides
    fun provideStepsRepository(context: Context): StepsRepository =
        StepsRepositoryImpl(ApolloProvider(context))

    @Provides
    fun provideEmotionsRepository(context: Context): EmotionsRepository = EmotionsRepositoryImpl(
        ApolloProvider(context)
    )
}