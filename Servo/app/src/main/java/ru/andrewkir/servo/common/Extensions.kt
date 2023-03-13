package ru.andrewkir.servo.common

import android.app.Activity
import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar


fun <activity : Activity> Activity.startActivityClearBackStack(activityClass: Class<activity>) {
    Intent(this, activityClass).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }
}