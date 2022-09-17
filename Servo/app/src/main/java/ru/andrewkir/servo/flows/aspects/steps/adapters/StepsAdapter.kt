package ru.andrewkir.servo.flows.aspects.steps.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.andrewkir.servo.R
import ru.andrewkir.servo.flows.aspects.finance.models.FinanceObject
import ru.andrewkir.servo.flows.aspects.steps.models.StepsObject
import java.text.SimpleDateFormat

class StepsAdapter(private var dataSet: List<StepsObject>, private val listener: (FinanceObject) -> Unit) :
    RecyclerView.Adapter<StepsAdapter.ViewHolder>() {

    fun setData(data: List<StepsObject>){
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

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.run {

            steps.text = dataSet[position].steps.toString()
            date.text = SimpleDateFormat("d MMMM").format(dataSet[position].date)

//            removeButton.setOnClickListener {
//                listener.invoke(dataSet[adapterPosition])
//                notifyItemRemoved(adapterPosition)
//            }
        }
    }

    override fun getItemCount() = dataSet.size

}