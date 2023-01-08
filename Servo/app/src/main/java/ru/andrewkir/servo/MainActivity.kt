package ru.andrewkir.servo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.andrewkir.servo.common.UserPrefsManager
import ru.andrewkir.servo.flows.auth.AuthActivity
import ru.andrewkir.servo.flows.main.MainScreenActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (!UserPrefsManager(this).accessToken.isNullOrBlank()) {
            startActivity(Intent(this, MainScreenActivity::class.java))
        } else {
            startActivity(Intent(this, AuthActivity::class.java))
        }
        finish()
    }
}