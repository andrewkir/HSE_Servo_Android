package ru.andrewkir.servo.flows.auth.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.andrewkir.data.repositories.AuthRepositoryImpl
import ru.andrewkir.domain.SigninUserMutation
import ru.andrewkir.domain.model.ApiResponse
import ru.andrewkir.domain.repositories.AuthRepository
import ru.andrewkir.servo.common.BaseViewModel
import javax.inject.Inject


class RegisterViewModel @Inject constructor(
    private val repo: AuthRepository
) : BaseViewModel(repo as AuthRepositoryImpl) {

    private val mutableLoginResponse: MutableLiveData<SigninUserMutation.Data> =
        MutableLiveData()

    val loginResponse: LiveData<SigninUserMutation.Data>
        get() = mutableLoginResponse

    fun register(username: String, password: String) {
        viewModelScope.launch {
            mutableLoading.value = true
            when (val result = repo.loginByEmail(username, password)) {
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