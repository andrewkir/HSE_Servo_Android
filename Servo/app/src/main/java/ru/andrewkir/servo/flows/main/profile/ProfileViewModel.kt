package ru.andrewkir.servo.flows.main.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.andrewkir.servo.common.BaseViewModel

class ProfileViewModel(
    private val profileRepository: ProfileRepository
) : BaseViewModel(profileRepository) {

}