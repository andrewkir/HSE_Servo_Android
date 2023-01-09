package ru.andrewkir.servo.flows.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.api.Optional
import kotlinx.coroutines.launch
import ru.andrewkir.SigninUserMutation
import ru.andrewkir.servo.common.BaseViewModel
import ru.andrewkir.servo.flows.auth.AuthRepository
import ru.andrewkir.servo.network.common.ApiResponse

class LoginViewModel(
    private val repo: AuthRepository
) : BaseViewModel(repo) {

    private val mutableLoginResponse: MutableLiveData<SigninUserMutation.Data> =
        MutableLiveData()

    val loginResponse: LiveData<SigninUserMutation.Data>
        get() = mutableLoginResponse

    fun loginByUsername(username: String, password: String) {
        viewModelScope.launch {
            mutableLoading.value = true
            when(val result = repo.loginByUsername(Optional.presentIfNotNull(username), password)){
                is ApiResponse.OnSuccessResponse -> {
                    mutableLoginResponse.value = result.value.data!!
                }
                is ApiResponse.OnErrorResponse -> {
                    errorResponse.value = result
                }
        }
            mutableLoading.value = false
        }
    }

    fun loginByEmail(email: String, password: String) {
        viewModelScope.launch {
            mutableLoading.value = true
            when(val result = repo.loginByEmail(Optional.presentIfNotNull(email), password)){
                is ApiResponse.OnSuccessResponse -> {
                    mutableLoginResponse.value = result.value.data!!
                }
                is ApiResponse.OnErrorResponse -> {
                    errorResponse.value = result
                }
            }
            mutableLoading.value = false
        }
    }
}