package ru.andrewkir.servo.flows.aspects.finance

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import ru.andrewkir.servo.App
import ru.andrewkir.servo.R
import ru.andrewkir.servo.common.BaseFragment
import ru.andrewkir.servo.databinding.FragmentAspectFinanceBinding
import ru.andrewkir.servo.flows.aspects.finance.adapters.FinanceAdapter
import ru.andrewkir.servo.flows.aspects.finance.models.FinanceCategoryEnum
import ru.andrewkir.servo.flows.aspects.finance.models.FinanceObject

class FinanceFragment : BaseFragment<FinanceViewModel, FinanceRepository, FragmentAspectFinanceBinding>() {

    override fun provideViewModel(): FinanceViewModel {
        (requireContext().applicationContext as App).appComponent.inject(this)
        return ViewModelProvider(this, viewModelFactory)[FinanceViewModel::class.java]
    }

    override fun provideBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAspectFinanceBinding =
        FragmentAspectFinanceBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupFinanceView(bind.chart, viewModel.getData())

        bind.button.setOnClickListener{
            findNavController().navigate(R.id.action_financeFragment_to_dashboardFragment)
        }

        bind.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        bind.recyclerView.adapter = FinanceAdapter(viewModel.getData())
    }

    companion object {
        fun setupFinanceView(chartView: PieChart, financeObjectList: ArrayList<FinanceObject>){
            chartView.let { pieChart ->
                var sum = 0.0
                val financeData = mutableMapOf<FinanceCategoryEnum, Float>()

                for (obj in financeObjectList){
                    if(obj.category in financeData.keys) {
                        financeData[obj.category] = financeData[obj.category]?.plus(obj.sum.toFloat()) ?: 0f
                    } else {
                        financeData[obj.category] = obj.sum.toFloat()
                    }
                    sum += obj.sum
                }

                val pieEntires: MutableList<PieEntry> = ArrayList()
                for (i in financeData.keys) {
                    pieEntires.add(PieEntry(financeData[i] ?: 0f,
                        when(i){
                            FinanceCategoryEnum.BANK_LOAN -> "Кредит"
                            FinanceCategoryEnum.GIVE_LOAN -> "Займ"
                            else -> "Одолжение"
                        }
                    ))
                }
                val dataSet = PieDataSet(pieEntires, "")
                dataSet.setColors(*ColorTemplate.MATERIAL_COLORS)
                val data = PieData(dataSet)
                data.setValueTextSize(0f)
                //Get the chart
                pieChart.data = data
                pieChart.invalidate()
                pieChart.centerText = sum.toString()
                pieChart.setDrawEntryLabels(false)
                pieChart.contentDescription = ""
                //pieChart.setDrawMarkers(true);
                //pieChart.setMaxHighlightDistance(34);
                //pieChart.setDrawMarkers(true);
                //pieChart.setMaxHighlightDistance(34);
                pieChart.setEntryLabelTextSize(12f)
                pieChart.holeRadius = 75f

                //legend attributes

                //legend attributes
                val legend: Legend = pieChart.legend
                legend.form = Legend.LegendForm.CIRCLE
                legend.textSize = 12f
                legend.formSize = 20f
                legend.formToTextSpace = 2f

                pieChart.description.isEnabled = false
            }
        }
    }
}