package ru.andrewkir.servo.flows.aspects.steps

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import kotlinx.coroutines.flow.collectLatest
import ru.andrewkir.servo.App
import ru.andrewkir.servo.R
import ru.andrewkir.servo.common.BaseFragment
import ru.andrewkir.servo.databinding.FragmentAspectStepsBinding
import ru.andrewkir.servo.flows.aspects.steps.adapters.StepsAdapter
import ru.andrewkir.servo.flows.aspects.steps.models.StepsModel
import ru.andrewkir.servo.flows.aspects.steps.models.StepsObject
import ru.andrewkir.type.DateTime
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.chrono.ChronoLocalDate
import java.time.format.DateTimeFormatter
import java.util.*


class StepsFragment :
    BaseFragment<StepsViewModel, StepsRepository, FragmentAspectStepsBinding>() {

    private lateinit var adapter: StepsAdapter

    private lateinit var selectedDate: Calendar
    private lateinit var weekAgoDate: Calendar

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

        bind.backButton.setOnClickListener {
            requireActivity().onBackPressed()
        }

        bind.newButton.setOnClickListener {
            showDialogNewSteps()
        }

        setupDate()

        adapter = StepsAdapter(emptyList()) {

        }

        bind.recyclerView.adapter = adapter
        bind.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        lifecycleScope.launchWhenCreated {
            viewModel.stepsData.collectLatest {
                updateStepsToDate()
                adapter.setData(it.stepsList)
            }
        }
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    private fun setupDate() {
        selectedDate = Calendar.getInstance()
        weekAgoDate = Calendar.getInstance()
        weekAgoDate.add(Calendar.DATE, -7)

        val sdf = SimpleDateFormat("d MMMM")

        bind.dateView.text = "с ${sdf.format(weekAgoDate.time).lowercase()} по ${
            sdf.format(selectedDate.time).lowercase()
        }"

        bind.nextDateButton.setOnClickListener {
            selectedDate.add(Calendar.DATE, 7)
            weekAgoDate.add(Calendar.DATE, 7)
            updateStepsToDate()
            bind.dateView.text = "с ${sdf.format(weekAgoDate.time).lowercase()} по ${
                sdf.format(selectedDate.time).lowercase()
            }"
        }

        bind.previousDateButton.setOnClickListener {
            selectedDate.add(Calendar.DATE, -7)
            weekAgoDate.add(Calendar.DATE, -7)
            updateStepsToDate()
            bind.dateView.text = "с ${sdf.format(weekAgoDate.time).lowercase()} по ${
                sdf.format(selectedDate.time).lowercase()
            }"
        }
    }

    private fun updateStepsToDate() {
        var steps = viewModel.stepsData.value.stepsList.filter {
            LocalDateTime.parse(
                it.date,
                DateTimeFormatter.ofPattern("E MMM d HH:mm:ss O yyyy", Locale.US)
            ).toLocalDate() >= LocalDateTime.parse(weekAgoDate.time.toString(), DateTimeFormatter.ofPattern("E MMM d HH:mm:ss O yyyy", Locale.US)).toLocalDate() && LocalDateTime.parse(
                it.date,
                DateTimeFormatter.ofPattern("E MMM d HH:mm:ss O yyyy", Locale.US)
            ).toLocalDate() < LocalDateTime.parse(selectedDate.time.toString(), DateTimeFormatter.ofPattern("E MMM d HH:mm:ss O yyyy", Locale.US)).toLocalDate()
        }
        setupStepsView(bind.barChart, StepsModel(steps))
        adapter.setData(steps)
    }

    companion object {
        fun setupStepsView(
            barChart: BarChart,
            stepsModel: StepsModel,
            isBackGroundWhite: Boolean = false
        ) {
            val dataset = getDataSet(stepsModel)
            val data = BarData(dataset.first)
            if (!isBackGroundWhite) dataset.first.valueTextColor = Color.WHITE
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
            barChart.xAxis.setDrawAxisLine(false)
            barChart.axisRight.setDrawGridLines(false)
            barChart.axisRight.setDrawLabels(false)
            barChart.axisRight.setDrawAxisLine(false)
            barChart.xAxis.textSize = 14f
            barChart.xAxis.granularity = 1f
            barChart.extraBottomOffset = 15f
            barChart.xAxis.spaceMin = 0.5f
            barChart.xAxis.spaceMax = 0.5f
            if (!isBackGroundWhite) barChart.xAxis.textColor = Color.WHITE
            barChart.xAxis.setAvoidFirstLastClipping(false)

            val formatter: ValueFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    if (dataset.second.isEmpty()) return ""
                    return dataset.second[value.toInt() % dataset.second.size]
                }
            }

            barChart.xAxis.valueFormatter = formatter

            barChart.invalidate()
        }


        @SuppressLint("SimpleDateFormat")
        private fun getDataSet(stepsModel: StepsModel): Pair<IBarDataSet, List<String>> {
            val valueSet = ArrayList<BarEntry>()
            val labels = ArrayList<String>()
            val stepsList = stepsModel.stepsList.sortedBy { it.date }
            for (i in stepsList.indices) {
                valueSet.add(BarEntry(i.toFloat(), stepsList[i].steps.toFloat()))
                val date = LocalDateTime.parse(
                    stepsList[i].date,
                    DateTimeFormatter.ofPattern("E MMM d HH:mm:ss O yyyy", Locale.US)
                ).toLocalDate()
                labels.add("${date.dayOfMonth}/${date.monthValue}")
            }

            val barDataSet = BarDataSet(valueSet, "")
            barDataSet.color = Color.parseColor("#5F58CD")
            return Pair(barDataSet, labels)
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun showDialogNewSteps() {
        val dialog = AlertDialog.Builder(requireContext())

        val view = layoutInflater.inflate(R.layout.dialog_new_steps, null)

        dialog.setView(view)
        val count = view.findViewById<EditText>(R.id.newStepsCount)
        val date = view.findViewById<TextView>(R.id.newStepsDate)

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
            .setTitle("Добавление шагов")
            .setPositiveButton("Ок") { _, _ -> }
            .setNeutralButton("Отмена") { dialog, _ -> dialog.dismiss() }
            .create()

        alertDialog.show()

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            var closeDialog = false
            if (count.text.isBlank()) {
                Toast.makeText(
                    requireContext(),
                    "Количество шагов не может быть пустым",
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
                viewModel.addSteps(
                    StepsObject(
                        date = SimpleDateFormat("dd.MM.yyyy").parse(date.text.toString())
                            .toString(),
                        steps = count.text.toString().toInt()
                    )
                )
            }
            if (closeDialog) alertDialog.dismiss()
        }
    }
}