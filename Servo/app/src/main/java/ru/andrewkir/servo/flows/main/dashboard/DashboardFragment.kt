package ru.andrewkir.servo.flows.main.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import ru.andrewkir.servo.App
import ru.andrewkir.servo.R
import ru.andrewkir.servo.common.BaseFragment
import ru.andrewkir.servo.common.BaseRepository
import ru.andrewkir.servo.common.BaseViewModel
import ru.andrewkir.servo.databinding.FragmentDashboardBinding
import ru.andrewkir.servo.flows.aspects.finance.FinanceCategoryEnum
import ru.andrewkir.servo.flows.aspects.finance.FinanceObject
import ru.andrewkir.servo.flows.aspects.finance.FinanceViewModel
import javax.inject.Inject
import kotlin.collections.ArrayList


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

        setupFinanceView()

        bind.financeAspectCardView.setOnClickListener{
            findNavController().navigate(R.id.action_dashboardFragment_to_financeFragment)
        }
    }

    private fun setupFinanceView(){
//        https://medium.com/@clyeung0714/using-mpandroidchart-for-android-application-piechart-123d62d4ddc0
        bind.chart.let { pieChart ->
            val financeObjectList = viewModel.getData()
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
                pieEntires.add(PieEntry(financeData[i] ?: 0f, i.name))
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