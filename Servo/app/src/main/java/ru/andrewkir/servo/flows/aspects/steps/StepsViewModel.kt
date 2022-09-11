package ru.andrewkir.servo.flows.aspects.steps

import ru.andrewkir.servo.common.BaseViewModel
import ru.andrewkir.servo.flows.aspects.finance.FinanceRepository
import javax.inject.Inject

class StepsViewModel @Inject constructor(
    val stepsRepository: StepsRepository
) : BaseViewModel(stepsRepository) {

}