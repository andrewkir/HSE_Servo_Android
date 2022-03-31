package ru.andrewkir.servo.flows.mainScreen

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.plusAssign
import androidx.navigation.ui.setupWithNavController
import ru.andrewkir.servo.R
import ru.andrewkir.servo.common.NavigatorSavingState
import ru.andrewkir.servo.databinding.ActivityMainScreenBinding

class MainScreenActivity : AppCompatActivity() {
    private lateinit var bind: ActivityMainScreenBinding

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMainScreenBinding.inflate(layoutInflater)
        setContentView(bind.root)

        val navHost = supportFragmentManager
            .findFragmentById(R.id.mainScreenNavHost) as NavHostFragment

        val navigator = NavigatorSavingState(this, navHost.childFragmentManager, R.id.mainScreenNavHost)
        navHost.navController.navigatorProvider += navigator

        navHost.navController.setGraph(R.navigation.nav_main)

        bind.bottomNavigationView.setupWithNavController(navHost.navController)
    }
}