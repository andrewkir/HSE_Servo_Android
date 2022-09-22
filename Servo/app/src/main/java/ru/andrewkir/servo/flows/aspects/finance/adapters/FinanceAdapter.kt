package ru.andrewkir.servo.flows.aspects.finance.adapters

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import ru.andrewkir.servo.R
import ru.andrewkir.servo.flows.aspects.finance.models.FinanceCategoryEnum
import ru.andrewkir.servo.flows.aspects.finance.models.FinanceObject
import java.text.SimpleDateFormat

class FinanceAdapter(private var dataSet: List<FinanceObject>, private val listener: (FinanceObject) -> Unit) :
    RecyclerView.Adapter<FinanceAdapter.ViewHolder>() {

    fun setData(data: List<FinanceObject>){
        dataSet = data
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.title_row)
        val sum: TextView = view.findViewById(R.id.sum_row)
        val date: TextView = view.findViewById(R.id.date_row)
        val color: CardView = view.findViewById(R.id.colorPlaceHolder)
        val removeButton: ImageView = view.findViewById(R.id.removeButton)

        init {

        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.finance_row_item, viewGroup, false)

        return ViewHolder(view)
    }

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.run {
            title.text = dataSet[position].name
            sum.text = dataSet[position].sum.toString()
            date.text = SimpleDateFormat("dd.MM.yyyy").format(dataSet[position].date)

            color.backgroundTintList = ColorStateList.valueOf(
                when(dataSet[position].category!!){
                    FinanceCategoryEnum.BANK_LOAN -> Color.parseColor("#6C64FF")
                    FinanceCategoryEnum.UNOFFICIAL_LOAN -> Color.parseColor("#5651AB")
                    FinanceCategoryEnum.GIVE_LOAN -> Color.parseColor("#8E8E93")
                }
            )

            removeButton.setOnClickListener {
                listener.invoke(dataSet[adapterPosition])
                notifyItemRemoved(adapterPosition)
            }
        }
    }

    override fun getItemCount() = dataSet.size

}