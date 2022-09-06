package ru.andrewkir.servo.flows.aspects.finance.adapters

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

class FinanceAdapter(private val dataSet: ArrayList<FinanceObject>, private val listener: (FinanceObject) -> Unit) :
    RecyclerView.Adapter<FinanceAdapter.ViewHolder>() {

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

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.run {
            title.text = dataSet[position].name
            sum.text = dataSet[position].sum.toString()
            date.text = "27 September"

            color.backgroundTintList = ColorStateList.valueOf(
                when(dataSet[position].category){
                    //TODO check
                    FinanceCategoryEnum.BANK_LOAN -> Color.parseColor("#2ecc71")
                    FinanceCategoryEnum.UNOFFICIAL_LOAN -> Color.parseColor("#f1c40f")
                    FinanceCategoryEnum.GIVE_LOAN -> Color.parseColor("#e74c3c")
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