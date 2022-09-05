package ru.andrewkir.servo.flows.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import ru.andrewkir.servo.R
import ru.andrewkir.servo.databinding.ActivityMainScreenBinding

class MainScreenActivity : AppCompatActivity() {
    private lateinit var bind: ActivityMainScreenBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMainScreenBinding.inflate(layoutInflater)
        setContentView(bind.root)

        val navHost = supportFragmentManager
            .findFragmentById(R.id.mainScreenNavHost) as NavHostFragment

        navController = navHost.navController
        bind.bottomNavigationView.setupWithNavController(navController)
    }
}