package ru.andrewkir.servo.flows.main.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.collectLatest
import ru.andrewkir.servo.App
import ru.andrewkir.servo.R
import ru.andrewkir.servo.common.BaseFragment
import ru.andrewkir.servo.databinding.FragmentDashboardBinding
import ru.andrewkir.servo.flows.aspects.finance.FinanceFragment.Companion.setupFinanceView
import ru.andrewkir.servo.flows.aspects.finance.models.FinanceModel
import ru.andrewkir.servo.flows.aspects.steps.StepsFragment.Companion.setupStepsView
import ru.andrewkir.servo.flows.aspects.steps.models.StepsModel
import ru.andrewkir.servo.flows.main.dashboard.adapters.DashboardAdapter
import ru.andrewkir.servo.flows.main.dashboard.adapters.DashboardAdapter.Companion.FINANCE_VIEW
import ru.andrewkir.servo.flows.main.dashboard.adapters.DashboardAdapter.Companion.STEPS_VIEW
import ru.andrewkir.servo.flows.main.dashboard.models.DashboardViews
import ru.andrewkir.servo.flows.main.dashboard.models.FinanceEntry
import ru.andrewkir.servo.flows.main.dashboard.models.StepsEntry


class DashboardFragment :
    BaseFragment<DashboardViewModel, DashboardRepository, FragmentDashboardBinding>() {

    private lateinit var adapter: DashboardAdapter

    private val itemTouchHelper by lazy {
        val simpleItemTouchCallback =
            object : ItemTouchHelper.SimpleCallback(
                UP or DOWN or START or END, 0
            ) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {

                    val adapter = recyclerView.adapter as DashboardAdapter
                    val from = viewHolder.adapterPosition
                    val to = target.adapterPosition
                    adapter.moveItem(from, to)
                    adapter.notifyItemMoved(from, to)

                    return true
                }

                override fun onSwiped(
                    viewHolder: RecyclerView.ViewHolder,
                    direction: Int
                ) {
                }

                override fun isLongPressDragEnabled(): Boolean {
                    return true
                }

                override fun isItemViewSwipeEnabled(): Boolean {
                    return false
                }
            }
        ItemTouchHelper(simpleItemTouchCallback)
    }

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

        adapter = DashboardAdapter {
            when (it) {
                DashboardViews.FinanceView -> findNavController().navigate(R.id.action_dashboardFragment_to_emotionsFragment)
                DashboardViews.StepsView -> findNavController().navigate(R.id.action_dashboardFragment_to_emotionsFragment)
            }
        }
        bind.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        bind.recyclerView.adapter = adapter
        adapter.data = viewModel.getCardData()
        itemTouchHelper.attachToRecyclerView(bind.recyclerView)

        lifecycleScope.launchWhenResumed {
            viewModel.financeFlow.collect {
                adapter.updateItem(FINANCE_VIEW, it.financeList)
            }
        }

        lifecycleScope.launchWhenResumed {
            viewModel.stepsFlow.collect {
                adapter.updateItem(STEPS_VIEW, it.stepsList)
            }
        }

        viewModel.getData()
        viewModel.getStepsData()
    }

    override fun onDestroyView() {
        viewModel.saveCardData(adapter.data)
        super.onDestroyView()
    }
}