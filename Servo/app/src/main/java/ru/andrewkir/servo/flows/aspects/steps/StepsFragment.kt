package ru.andrewkir.servo.flows.aspects.steps

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.BarLineChartBase
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import ru.andrewkir.servo.App
import ru.andrewkir.servo.R
import ru.andrewkir.servo.common.BaseFragment
import ru.andrewkir.servo.databinding.FragmentAspectStepsBinding
import ru.andrewkir.servo.flows.aspects.finance.adapters.FinanceAdapter
import ru.andrewkir.servo.flows.aspects.finance.models.FinanceCategoryEnum
import ru.andrewkir.servo.flows.aspects.finance.models.FinanceObject
import java.util.*
import kotlin.collections.ArrayList


class StepsFragment :
    BaseFragment<StepsViewModel, StepsRepository, FragmentAspectStepsBinding>() {

    private lateinit var adapter: FinanceAdapter

    override fun provideViewModel(): StepsViewModel {
        (requireContext().applicationContext as App).appComponent.inject(this)
        return ViewModelProvider(this, viewModelFactory)[StepsViewModel::class.java]
    }

    override fun provideBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAspectStepsBinding =
        FragmentAspectStepsBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bind.button.setOnClickListener {
            findNavController().navigate(R.id.action_stepsFragment_to_dashboardFragment)
        }

        bind.newButton.setOnClickListener {
            showDialogNewLoan()
        }

        setupStepsView(bind.barChart)

    }

    companion object {
        fun setupStepsView(barChart: BarChart) {
            val dataset = getDataSet()
            val data = BarData(dataset.first)
            data.barWidth = 0.5f
            data.setValueTextSize(14f)
            barChart.data = data
            barChart.animateXY(1000, 1000)
            barChart.xAxis.setDrawGridLines(false)
            barChart.axisLeft.setDrawGridLines(false)
            barChart.axisRight.setDrawGridLines(false)
            barChart.isDragEnabled = false
            barChart.setDrawGridBackground(false)
            barChart.setTouchEnabled(false)
            barChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
            barChart.setTouchEnabled(true)
            barChart.isClickable = false
            barChart.isDoubleTapToZoomEnabled = false
            barChart.setDrawBorders(false)
            barChart.setDrawGridBackground(false)
            barChart.description.isEnabled = false
            barChart.legend.isEnabled = false
            barChart.axisLeft.setDrawGridLines(false)
            barChart.axisLeft.setDrawLabels(false)
            barChart.axisLeft.setDrawAxisLine(false)
//      bind.barChart.xAxis.setDrawAxisLine(false)
            barChart.axisRight.setDrawGridLines(false)
            barChart.axisRight.setDrawLabels(false)
            barChart.axisRight.setDrawAxisLine(false)
            barChart.xAxis.textSize = 14f
            barChart.xAxis.granularity = 1f
            barChart.extraBottomOffset = 15f
            barChart.xAxis.setAvoidFirstLastClipping(true)

            val formatter: ValueFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    return dataset.second[(value.toInt() - 1) % dataset.second.size]
                }
            }

            barChart.xAxis.valueFormatter = formatter

            barChart.invalidate()
        }


        private fun getDataSet(): Pair<IBarDataSet, List<String>> {
            val valueSet = ArrayList<BarEntry>()
            val labels = ArrayList<String>()
            for (i in 1..5){
                valueSet.add(BarEntry(i.toFloat(), Random().nextInt(1000).toFloat()))
                labels.add("$i.9")
            }

            val barDataSet = BarDataSet(valueSet, "")
            barDataSet.color = Color.rgb(0, 155, 0)
            return Pair(barDataSet, labels)
        }
    }

//    private fun getXAxisValues(): BarDataSet {
//        return BarDataSet(date, "depenses")
//    }


    @SuppressLint("SimpleDateFormat")
    private fun showDialogNewLoan() {
        val dialog = AlertDialog.Builder(requireContext())

        val view = layoutInflater.inflate(R.layout.dialog_new_loan, null)

        dialog.setView(view)
        val name = view.findViewById<EditText>(R.id.newLoanName)
        val sum = view.findViewById<EditText>(R.id.newLoanSum)
        val date = view.findViewById<TextView>(R.id.newLoanDate)

        date.setOnClickListener {
            val c = Calendar.getInstance()
            val datePickerDialog = DatePickerDialog(
                it.context,
                { _, year, monthOfYear, dayOfMonth ->
                    date.text = dayOfMonth.toString() + "." + (monthOfYear + 1) + "." + year
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        }

        val alertDialog = dialog
            .setTitle("Добавление долга")
            .setPositiveButton("Ок") { _, _ -> }
            .setNeutralButton("Отмена") { dialog, _ -> dialog.dismiss() }
            .create()

        alertDialog.show()

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            var closeDialog = false
            if (name.text.isBlank()) {
                Toast.makeText(
                    requireContext(),
                    "Название не может быть пустым",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (sum.text.isBlank()) {
                Toast.makeText(
                    requireContext(),
                    "Сумма не может быть пустой",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (date.text.toString() == "нажмите здесь") {
                Toast.makeText(
                    requireContext(),
                    "Дата не может быть пустой",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                closeDialog = true
            }
            if (closeDialog) alertDialog.dismiss()
        }
    }
}