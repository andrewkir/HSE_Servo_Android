package ru.andrewkir.servo.flows.main.dashboard.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import ru.andrewkir.servo.R
import ru.andrewkir.servo.flows.aspects.finance.FinanceFragment.Companion.setupFinanceView
import ru.andrewkir.servo.flows.aspects.steps.StepsFragment.Companion.setupStepsView
import ru.andrewkir.servo.flows.main.dashboard.models.DashboardModel
import ru.andrewkir.servo.flows.main.dashboard.models.DashboardViews
import ru.andrewkir.servo.flows.main.dashboard.models.FinanceEntry
import ru.andrewkir.servo.flows.main.dashboard.models.StepsEntry

class DashboardAdapter(
    private val onItemClick: ((DashboardViews) -> Unit)? = null
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val FINANCE_VIEW = 0
        const val STEPS_VIEW = 1
    }

    var data: MutableList<DashboardModel> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            FINANCE_VIEW -> FinanceViewHolder(
                LayoutInflater.from(viewGroup.context)
                    .inflate(R.layout.finance_view_row, viewGroup, false)
            )
            else -> StepsViewHolder(
                LayoutInflater.from(viewGroup.context)
                    .inflate(R.layout.steps_view_row, viewGroup, false)
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (data[position].type) {
            DashboardViews.FinanceView -> FINANCE_VIEW
            DashboardViews.StepsView -> STEPS_VIEW
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        when (viewHolder.itemViewType) {
            FINANCE_VIEW -> {
                (viewHolder as FinanceViewHolder).title?.text = data[position].title

                viewHolder.card?.setOnClickListener { onItemClick?.invoke(data[position].type) }

                setupFinanceView(
                    viewHolder.chart!!,
                    (data[position] as FinanceEntry)
                        .data.financeList,
                    true
                )
            }
            STEPS_VIEW -> {
                (viewHolder as StepsViewHolder).title?.text = data[position].title

                viewHolder.card?.setOnClickListener { onItemClick?.invoke(data[position].type) }

                setupStepsView(
                    viewHolder.chart!!,
                    (data[position] as StepsEntry)
                        .data,
                    true
                )
            }
        }
    }

    inner class FinanceViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView? = null
        var chart: PieChart? = null
        var card: CardView? = null

        init {
            title = view.findViewById(R.id.financeTitleText)
            chart = view.findViewById(R.id.chart)
            card = view.findViewById(R.id.financeAspectCardView)
        }
    }

    inner class StepsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView? = null
        var chart: BarChart? = null
        var card: CardView? = null

        init {
            title = view.findViewById(R.id.stepsTitleText)
            chart = view.findViewById(R.id.stepsBarChart)
            card = view.findViewById(R.id.stepsAspectCardView)
        }
    }

    override fun getItemCount() = data.size

    fun moveItem(from: Int, to: Int) {
        val fromData = data[from]
        data.removeAt(from)
        if (to < from) {
            data.add(to, fromData)
        } else {
            data.add(to - 1, fromData)
        }
    }
}