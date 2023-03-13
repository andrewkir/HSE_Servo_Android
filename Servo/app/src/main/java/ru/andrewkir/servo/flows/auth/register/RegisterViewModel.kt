package ru.andrewkir.servo.flows.auth.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.andrewkir.domain.SignupUserMutation
import ru.andrewkir.domain.model.ApiResponse
import ru.andrewkir.domain.repositories.AuthRepository
import ru.andrewkir.servo.common.BaseViewModel
import javax.inject.Inject


class RegisterViewModel @Inject constructor(
    private val repo: AuthRepository
) : BaseViewModel() {

    private val mutableLoginResponse: MutableLiveData<SignupUserMutation.Data> =
        MutableLiveData()

    val loginResponse: LiveData<SignupUserMutation.Data>
        get() = mutableLoginResponse

    fun register(email:String ,username: String, password: String) {
        viewModelScope.launch {
            mutableLoading.value = true
            when (val result = repo.register(email = email,username = username, password = password, firstName = username, lastName = username)) {
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