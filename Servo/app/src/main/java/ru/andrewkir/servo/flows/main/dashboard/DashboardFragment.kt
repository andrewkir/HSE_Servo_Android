package ru.andrewkir.servo.flows.main.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ru.andrewkir.servo.App
import ru.andrewkir.servo.R
import ru.andrewkir.servo.common.BaseFragment
import ru.andrewkir.servo.databinding.FragmentDashboardBinding
import ru.andrewkir.servo.flows.aspects.finance.FinanceFragment.Companion.setupFinanceView


class DashboardFragment :
    BaseFragment<DashboardViewModel, DashboardRepository, FragmentDashboardBinding>() {

    override fun provideBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDashboardBinding =
        FragmentDashboardBinding.inflate(inflater, container, false)

    override fun provideViewModel(): DashboardViewModel {
            (requireContext().applicationContext as App).appComponent.inject(this)
            return ViewModelProvider(this, viewModelFactory)[DashboardViewModel::class.java]
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupFinanceView(bind.chart, viewModel.getData())

        bind.financeAspectCardView.setOnClickListener{
            findNavController().navigate(R.id.action_dashboardFragment_to_financeFragment)
        }
    }
}