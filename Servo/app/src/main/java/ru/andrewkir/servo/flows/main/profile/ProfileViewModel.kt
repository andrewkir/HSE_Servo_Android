package ru.andrewkir.servo.flows.main.profile

import ru.andrewkir.servo.common.BaseViewModel
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository
) : BaseViewModel() {

}