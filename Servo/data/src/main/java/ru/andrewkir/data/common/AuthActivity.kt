package ru.andrewkir.data.common

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.andrewkir.data.R

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
    }
}