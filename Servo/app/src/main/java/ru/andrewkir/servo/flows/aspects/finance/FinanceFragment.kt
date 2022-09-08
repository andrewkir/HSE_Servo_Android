package ru.andrewkir.servo.flows.aspects.finance

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.android.material.chip.Chip
import kotlinx.coroutines.launch
import ru.andrewkir.servo.App
import ru.andrewkir.servo.R
import ru.andrewkir.servo.common.BaseFragment
import ru.andrewkir.servo.databinding.FragmentAspectFinanceBinding
import ru.andrewkir.servo.flows.aspects.finance.adapters.FinanceAdapter
import ru.andrewkir.servo.flows.aspects.finance.models.FinanceCategoryEnum
import ru.andrewkir.servo.flows.aspects.finance.models.FinanceObject
import java.text.SimpleDateFormat
import java.util.*


class FinanceFragment :
    BaseFragment<FinanceViewModel, FinanceRepository, FragmentAspectFinanceBinding>() {

    private lateinit var adapter: FinanceAdapter

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

        bind.button.setOnClickListener {
            findNavController().navigate(R.id.action_financeFragment_to_dashboardFragment)
        }

        bind.newButton.setOnClickListener {
            showDialogNewLoan()
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.financeData.collect() {
                    setupFinanceView(bind.chart, it.financeList)
                    adapter.setData(it.financeList)
                }
            }
        }

        adapter = FinanceAdapter(emptyList()) {
            viewModel.removeLoan(it)
            setupFinanceView(bind.chart, viewModel.mFinanceData)
            adapter.setData(viewModel.mFinanceData)
        }
        bind.recyclerView.adapter = adapter
        bind.recyclerView.layoutManager = LinearLayoutManager(requireContext())


    }

    private fun showDialogNewLoan() {
        val dialog = AlertDialog.Builder(requireContext())

        val view = layoutInflater.inflate(R.layout.dialog_new_loan, null)

        dialog.setView(view)
        val name = view.findViewById<EditText>(R.id.newLoanName)
        val sum = view.findViewById<EditText>(R.id.newLoanSum)
        val date = view.findViewById<TextView>(R.id.newLoanDate)

        val giveLoanChip = view.findViewById<Chip>(R.id.giveLoanChip)
        val bankLoanChip = view.findViewById<Chip>(R.id.bankLoanChip)
        val unofficialLoanChip = view.findViewById<Chip>(R.id.unofficialLoanChip)

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
                viewModel.addLoan(
                    FinanceObject(
                        name = name.text.toString(),
                        sum = sum.text.toString().toDouble(),
                        date = SimpleDateFormat("dd.MM.yyyy").parse(date.text.toString()),
                        category = if (bankLoanChip.isChecked) FinanceCategoryEnum.BANK_LOAN else
                            if (giveLoanChip.isChecked) FinanceCategoryEnum.GIVE_LOAN else FinanceCategoryEnum.UNOFFICIAL_LOAN
                    )
                )
                adapter.setData(viewModel.mFinanceData)
                setupFinanceView(bind.chart, viewModel.mFinanceData)
            }
            if (closeDialog) alertDialog.dismiss()
        }
    }

    companion object {
        fun setupFinanceView(chartView: PieChart, financeObjectList: List<FinanceObject>) {
            chartView.let { pieChart ->
                var sum = 0.0
                val financeData = mutableMapOf<FinanceCategoryEnum, Float>()

                for (obj in financeObjectList) {
                    if (obj.category in financeData.keys) {
                        financeData[obj.category] =
                            financeData[obj.category]?.plus(obj.sum.toFloat()) ?: 0f
                    } else {
                        financeData[obj.category] = obj.sum.toFloat()
                    }
                    sum += obj.sum
                }

                val pieEntires: MutableList<PieEntry> = ArrayList()
                for (i in financeData.keys.sortedBy { it.ordinal }) {
                    pieEntires.add(
                        PieEntry(
                            financeData[i] ?: 0f,
                            when (i) {
                                FinanceCategoryEnum.BANK_LOAN -> "Кредит"
                                FinanceCategoryEnum.GIVE_LOAN -> "Одолжение"
                                else -> "Займ"
                            }
                        )
                    )
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
                pieChart.setEntryLabelTextSize(12f)
                pieChart.holeRadius = 75f

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