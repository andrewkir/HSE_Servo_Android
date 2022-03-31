package ru.andrewkir.servo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.andrewkir.servo.flows.auth.AuthActivity
import ru.andrewkir.servo.flows.mainScreen.MainScreenActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startActivity(Intent(this, MainScreenActivity::class.java))
    }
}