package ru.andrewkir.servo.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.andrewkir.domain.model.ApiResponse

abstract class BaseViewModel : ViewModel() {

    protected val mutableLoading: MutableLiveData<Boolean> = MutableLiveData()

    val loading: LiveData<Boolean>
        get() = mutableLoading


    val errorResponse: SingleLiveEvent<ApiResponse.OnErrorResponse> by lazy {
        SingleLiveEvent()
    }
}