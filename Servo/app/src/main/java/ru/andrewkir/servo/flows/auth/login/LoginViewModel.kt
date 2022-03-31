package ru.andrewkir.servo.flows.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.andrewkir.GetCharactersQuery
import ru.andrewkir.servo.common.BaseViewModel
import ru.andrewkir.servo.flows.auth.AuthRepository

class LoginViewModel(
    private val repo: AuthRepository
) : BaseViewModel(repo) {

    private val mutableLoginResponse: MutableLiveData<GetCharactersQuery.Data> =
        MutableLiveData()

    val loginResponse: LiveData<GetCharactersQuery.Data>
        get() = mutableLoginResponse

    fun loginByUsername(username: String, password: String) {
        viewModelScope.launch {
            mutableLoading.value = true
//            when(val result = repo.loginByEmail(username, password)){
//                is ApiResponse.OnSuccessResponse -> {
//                    mutableLoginResponse.value = result.value.data!!
//                }
//        }
            mutableLoading.value = false
        }
    }
}