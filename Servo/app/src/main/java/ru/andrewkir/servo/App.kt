package ru.andrewkir.servo

import android.app.Application
import ru.andrewkir.servo.di.AppComponent
import ru.andrewkir.servo.di.AppModule
import ru.andrewkir.servo.di.DaggerAppComponent

class App : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule(context = this))
            .build()
    }

}