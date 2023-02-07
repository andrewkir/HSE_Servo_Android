package ru.andrewkir.servo.flows.main.dashboard.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import ru.andrewkir.servo.R
import ru.andrewkir.servo.flows.aspects.emotions.EmotionsFragment.Companion.setupEmotionsView
import ru.andrewkir.domain.model.EmotionsModel
import ru.andrewkir.servo.flows.aspects.finance.FinanceFragment.Companion.setupFinanceView
import ru.andrewkir.domain.model.FinanceModel
import ru.andrewkir.domain.model.FinanceObject
import ru.andrewkir.servo.flows.aspects.steps.StepsFragment.Companion.setupStepsView
import ru.andrewkir.domain.model.StepsModel
import ru.andrewkir.domain.model.StepsObject
import ru.andrewkir.servo.flows.main.dashboard.models.*

class DashboardAdapter(
    private val onItemClick: ((DashboardViews) -> Unit)? = null
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val FINANCE_VIEW = 0
        const val STEPS_VIEW = 1
        const val EMOTIONS_VIEW = 2
    }

    @Suppress("UNCHECKED_CAST")
    fun updateItem(viewType: Int, data: List<*>) {
        if (this.data.isEmpty()) return
        when (viewType) {
            FINANCE_VIEW -> {
                (this.data.firstOrNull { it.type == DashboardViews.FinanceView } as FinanceEntry)
                    .data = FinanceModel(data as List<FinanceObject>)
                notifyDataSetChanged()
            }
            STEPS_VIEW -> {
                (this.data.firstOrNull { it.type == DashboardViews.StepsView } as StepsEntry)
                    .data = StepsModel(data as List<StepsObject>)
                notifyDataSetChanged()
            }
            EMOTIONS_VIEW -> {
                (this.data.firstOrNull { it.type == DashboardViews.EmotionsView } as EmotionsEntry)
                    .data = data as List<EmotionsModel>
                notifyDataSetChanged()
            }
        }
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
            STEPS_VIEW -> StepsViewHolder(
                LayoutInflater.from(viewGroup.context)
                    .inflate(R.layout.steps_view_row, viewGroup, false)
            )
            else -> EmotionsViewHolder(
                LayoutInflater.from(viewGroup.context)
                    .inflate(R.layout.emotions_view_row, viewGroup, false)
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (data[position].type) {
            DashboardViews.FinanceView -> FINANCE_VIEW
            DashboardViews.StepsView -> STEPS_VIEW
            DashboardViews.EmotionsView -> EMOTIONS_VIEW
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        when (viewHolder.itemViewType) {
            FINANCE_VIEW -> {
                (viewHolder as FinanceViewHolder).title?.text = data[position].title

                viewHolder.card?.setOnClickListener { onItemClick?.invoke(DashboardViews.FinanceView) }

                setupFinanceView(
                    viewHolder.chart!!,
                    (data[position] as FinanceEntry)
                        .data.financeList,
                    true
                )
            }
            STEPS_VIEW -> {
                (viewHolder as StepsViewHolder).title?.text = data[position].title

                viewHolder.card?.setOnClickListener { onItemClick?.invoke(DashboardViews.StepsView) }

                setupStepsView(
                    viewHolder.chart!!,
                    (data[position] as StepsEntry)
                        .data,
                    true
                )
            }
            EMOTIONS_VIEW -> {
                (viewHolder as EmotionsViewHolder).title?.text = data[position].title

                viewHolder.card?.setOnClickListener { onItemClick?.invoke(DashboardViews.EmotionsView) }

                setupEmotionsView(
                    viewHolder.card?.context!!,
                    (data[position] as EmotionsEntry).data!!,
                    true,
                    viewHolder.icon!!
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

    inner class EmotionsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView? = null
        var card: CardView? = null
        var description: TextView? = null
        var icon: ImageView? = null

        init {
            title = view.findViewById(R.id.emotionsTitleText)
            card = view.findViewById(R.id.emotionsAspectCardVied)
            description = view.findViewById(R.id.descriptionText)
            icon = view.findViewById(R.id.emotionsIcon)
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