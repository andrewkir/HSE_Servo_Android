package ru.andrewkir.servo.flows.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.andrewkir.domain.SigninUserMutation
import ru.andrewkir.domain.model.ApiResponse
import ru.andrewkir.domain.repositories.AuthRepository
import ru.andrewkir.servo.common.BaseViewModel
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val repo: AuthRepository
) : BaseViewModel() {

    private val mutableLoginResponse: MutableLiveData<SigninUserMutation.Data> =
        MutableLiveData()

    val loginResponse: LiveData<SigninUserMutation.Data>
        get() = mutableLoginResponse

    fun loginByUsername(username: String, password: String) {
        viewModelScope.launch {
            mutableLoading.value = true
            when(val result = repo.loginByUsername(username, password)){
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
            when(val result = repo.loginByEmail(email, password)){
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