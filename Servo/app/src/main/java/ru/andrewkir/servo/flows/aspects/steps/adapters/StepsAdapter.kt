package ru.andrewkir.servo.flows.aspects.steps.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.andrewkir.servo.R
import ru.andrewkir.domain.model.FinanceObject
import ru.andrewkir.domain.model.StepsObject
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class StepsAdapter(
    private var dataSet: List<StepsObject>,
    private val listener: (FinanceObject) -> Unit
) :
    RecyclerView.Adapter<StepsAdapter.ViewHolder>() {

    fun setData(data: List<StepsObject>) {
        dataSet = data.sortedBy { it.date }
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val date: TextView = view.findViewById(R.id.steps_date_row)
        val steps: TextView = view.findViewById(R.id.steps_count)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.steps_row_item, viewGroup, false)

        return ViewHolder(view)
    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.run {

            steps.text = dataSet[position].steps.toString()
            val mdate = LocalDateTime.parse(
                dataSet[position].date,
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
            ).toLocalDate()

            date.text = "${mdate.dayOfMonth} ${
                DateTimeFormatter.ofPattern(
                    "MMMM",
                    Locale.getDefault()
                ).format(mdate)
            }"

//            removeButton.setOnClickListener {
//                listener.invoke(dataSet[adapterPosition])
//                notifyItemRemoved(adapterPosition)
//            }
        }
    }

    override fun getItemCount() = dataSet.size

}