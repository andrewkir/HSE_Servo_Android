package ru.andrewkir.servo.flows.aspects.emotions

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.TransitionDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import ru.andrewkir.servo.App
import ru.andrewkir.servo.R
import ru.andrewkir.servo.common.BaseFragment
import ru.andrewkir.servo.common.ViewModelFactory
import ru.andrewkir.servo.databinding.FragmentAspectEmotionsBinding
import ru.andrewkir.servo.flows.aspects.emotions.adapters.EmotionsAdapter
import ru.andrewkir.servo.flows.aspects.emotions.models.Emotions.*
import ru.andrewkir.servo.flows.aspects.emotions.models.EmotionsModel
import ru.andrewkir.servo.flows.aspects.finance.FinanceFragment
import ru.andrewkir.servo.flows.aspects.finance.adapters.FinanceAdapter
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.logging.Level.parse
import javax.inject.Inject


class EmotionsFragment :
    BaseFragment<EmotionsViewModel, EmotionsRepository, FragmentAspectEmotionsBinding>() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    var curDate: Date = Date()

    lateinit var adapter: EmotionsAdapter

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

        bind.calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            curDate = SimpleDateFormat("MM-dd-yyyy").parse("${month+1}-${dayOfMonth}-${year}")
        }

        adapter = EmotionsAdapter(emptyList()) {
            viewModel.removeEmotion(it)
            adapter.setData(viewModel.mEmotionsData)
        }
        bind.recyclerView.adapter = adapter
        bind.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.emotionsData.collect() {
                    adapter.setData(it)
                }
            }
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
                Log.d("TEST", curDate.toString())
                viewModel.addEmotion(
                    EmotionsModel(
                        UUID.randomUUID().toString(),
                        if (isHappySelected) HAPPY else if (isPokerFaceSelected) NORMAL else SAD,
                        comment.text.toString(),
                        date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(curDate)
                    )
                )
                closeDialog = true
            }
            if (closeDialog) alertDialog.dismiss()
        }
    }

    companion object {
        fun setupEmotionsView(
            context: Context,
            emmotionsList: List<EmotionsModel>,
            isBackGroundWhite: Boolean,
            icon: ImageView
        ) {
            val maxOccurring = emmotionsList.groupBy { it }.mapValues { it.value.size }
                .maxByOrNull { it.value }?.key
            when (maxOccurring?.emotion) {
                HAPPY -> {
                    icon.setImageDrawable(context.resources.getDrawable(R.drawable.happy))
                }
                SAD -> {
                    icon.setImageDrawable(context.resources.getDrawable(R.drawable.sad))
                }
                NORMAL -> {
                    icon.setImageDrawable(context.resources.getDrawable(R.drawable.poker_face))
                }
                else -> {}
            }
        }
    }
}