package ru.andrewkir.servo.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.andrewkir.servo.flows.aspects.emotions.EmotionsRepository
import ru.andrewkir.servo.flows.aspects.finance.FinanceRepository
import ru.andrewkir.servo.flows.aspects.steps.StepsRepository
import ru.andrewkir.servo.flows.auth.AuthRepository
import ru.andrewkir.servo.flows.main.dashboard.DashboardRepository
import ru.andrewkir.servo.flows.main.profile.ProfileRepository
import ru.andrewkir.servo.network.ApolloProvider

@Module
class DataModule {
    @Provides
    fun provideFinanceRepository(): FinanceRepository = FinanceRepository()
    @Provides
    fun provideAuthRepository(context: Context): AuthRepository = AuthRepository(ApolloProvider(context))
    @Provides
    fun provideProfileRepository(): ProfileRepository = ProfileRepository()
    @Provides
    fun provideDashBoardRepository(): DashboardRepository = DashboardRepository()
    @Provides
    fun provideStepsRepository(): StepsRepository = StepsRepository()
    @Provides
    fun provideEmotionsRepository(): EmotionsRepository = EmotionsRepository()
}