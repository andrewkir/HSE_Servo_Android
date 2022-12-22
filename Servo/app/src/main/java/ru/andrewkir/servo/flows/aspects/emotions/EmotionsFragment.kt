package ru.andrewkir.servo.flows.aspects.emotions

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.graphics.drawable.TransitionDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.chip.Chip
import ru.andrewkir.servo.App
import ru.andrewkir.servo.R
import ru.andrewkir.servo.common.BaseFragment
import ru.andrewkir.servo.databinding.FragmentAspectEmotionsBinding
import ru.andrewkir.servo.flows.aspects.emotions.models.Emotions
import ru.andrewkir.servo.flows.aspects.emotions.models.EmotionsModel
import ru.andrewkir.servo.flows.aspects.finance.FinanceFragment
import ru.andrewkir.servo.flows.aspects.finance.models.FinanceCategoryEnum
import ru.andrewkir.servo.flows.aspects.finance.models.FinanceObject
import java.text.SimpleDateFormat
import java.util.*

class EmotionsFragment :
    BaseFragment<EmotionsViewModel, EmotionsRepository, FragmentAspectEmotionsBinding>() {

    override fun provideViewModel(): EmotionsViewModel {
        (requireContext().applicationContext as App).appComponent.inject(this)
        return ViewModelProvider(this, viewModelFactory)[EmotionsViewModel::class.java]
    }

    override fun provideBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAspectEmotionsBinding =
        FragmentAspectEmotionsBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bind.backButton.setOnClickListener {
            requireActivity().onBackPressed()
        }

        bind.newButton.setOnClickListener {
            showDialogNewEmotion()
        }
    }

    private lateinit var happyView: ImageView
    private var isHappySelected = false
        set(value) {
            if (value != isHappySelected) {
                if (value) {
                    (happyView.drawable as TransitionDrawable).startTransition(500)
                } else {
                    (happyView.drawable as TransitionDrawable).reverseTransition(500)
                }
                field = value
            }
        }
    lateinit var pokerFaceView: ImageView
    private var isPokerFaceSelected = false
        set(value) {
            if (value != isPokerFaceSelected) {
                if (value) {
                    (pokerFaceView.drawable as TransitionDrawable).startTransition(500)
                } else {
                    (pokerFaceView.drawable as TransitionDrawable).reverseTransition(500)
                }
                field = value
            }
        }
    lateinit var sadView: ImageView
    private var isSadSelected = false
        set(value) {
            if (value != isSadSelected) {
                if (value) {
                    (sadView.drawable as TransitionDrawable).startTransition(500)
                } else {
                    (sadView.drawable as TransitionDrawable).reverseTransition(500)
                }
                field = value
            }
        }

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    private fun showDialogNewEmotion() {
        val dialog = AlertDialog.Builder(requireContext())

        val view = layoutInflater.inflate(R.layout.dialog_new_emotion, null)

        happyView = view.findViewById(R.id.happyView)
        pokerFaceView = view.findViewById(R.id.pokerfaceView)
        sadView = view.findViewById(R.id.sadView)

        val comment = view.findViewById<EditText>(R.id.comment)

        happyView.setOnClickListener {
            isHappySelected = !isHappySelected
            isPokerFaceSelected = false
            isSadSelected = false
        }
        pokerFaceView.setOnClickListener {
            isHappySelected = false
            isPokerFaceSelected = !isPokerFaceSelected
            isSadSelected = false
        }
        sadView.setOnClickListener {
            isHappySelected = false
            isPokerFaceSelected = false
            isSadSelected = !isSadSelected
        }

        dialog.setView(view)

        val alertDialog = dialog
            .setTitle("Добавление эмоции")
            .setPositiveButton("Ок") { _, _ -> }
            .setNeutralButton("Отмена") { dialog, _ -> dialog.dismiss() }
            .create()

        alertDialog.show()

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            var closeDialog = false
            if (!isHappySelected && !isPokerFaceSelected && !isSadSelected) {
                Toast.makeText(requireContext(), "Выберите одну из эмоций", Toast.LENGTH_SHORT)
                    .show()
            } else {
                viewModel.addEmotion(
                    EmotionsModel(
                        if (isHappySelected) Emotions.HAPPY else if (isPokerFaceSelected) Emotions.NORMAL else Emotions.SAD,
                        comment.text.toString()
                    ),
                    Date(bind.calendarView.date)
                )
                closeDialog = true
            }
            if (closeDialog) alertDialog.dismiss()
        }
    }
}