package ru.andrewkir.servo.flows.aspects.emotions.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.andrewkir.servo.R
import ru.andrewkir.domain.model.Emotions
import ru.andrewkir.domain.model.EmotionsModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class EmotionsAdapter(
    private var dataSet: List<EmotionsModel>,
    private val listener: (EmotionsModel) -> Unit
) :
    RecyclerView.Adapter<EmotionsAdapter.ViewHolder>() {

    fun setData(data: List<EmotionsModel>) {
        dataSet = data
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val description: TextView = view.findViewById(R.id.descriptionText)
        val icon: ImageView = view.findViewById(R.id.emotionIcon)
        val date: TextView = view.findViewById(R.id.dateText)
        val removeButton: ImageView = view.findViewById(R.id.removeButton)

        init {

        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.emotions_row_item, viewGroup, false)

        return ViewHolder(view)
    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n", "UseCompatLoadingForDrawables")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.run {
            description.text = dataSet[position].comment
            if (dataSet[position].date != null) {
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
            }
            when (dataSet[position].emotion) {
                Emotions.HAPPY -> {
                    icon.setImageDrawable(viewHolder.icon.context.resources.getDrawable(R.drawable.happy))
                }
                Emotions.SAD -> {
                    icon.setImageDrawable(viewHolder.icon.context.resources.getDrawable(R.drawable.sad))
                }
                Emotions.NORMAL -> {
                    icon.setImageDrawable(viewHolder.icon.context.resources.getDrawable(R.drawable.poker_face))
                }
                else -> {}
            }
            removeButton.setOnClickListener {
                listener.invoke(dataSet[adapterPosition])
                notifyItemRemoved(adapterPosition)
            }
        }
    }

    override fun getItemCount() = dataSet.size

}