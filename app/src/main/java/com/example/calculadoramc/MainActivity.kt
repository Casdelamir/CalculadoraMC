package com.example.calculadoramc

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.google.android.material.slider.Slider
import java.util.logging.Logger
import kotlin.math.pow

class MainActivity : AppCompatActivity() {

    lateinit var heightText: EditText
    lateinit var weightText: TextView
    lateinit var addButton: Button
    lateinit var restButton: Button
    lateinit var calculateButton: Button
    lateinit var result: TextView
    lateinit var resultClassification: TextView
    lateinit var slider: Slider
    lateinit var card: CardView

    val handler = Handler(Looper.getMainLooper())
    lateinit var runnableSum: Runnable
    lateinit var runnableRest: Runnable

    lateinit var logger: Logger

    var height = 150
    var weight = 60

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        heightText = findViewById(R.id.valueHeight)
        weightText = findViewById(R.id.valueWeight)
        addButton = findViewById(R.id.addButton)
        restButton = findViewById(R.id.restButton)
        calculateButton = findViewById(R.id.calculate)
        result = findViewById(R.id.result)
        resultClassification = findViewById(R.id.result_classification)
        slider = findViewById(R.id.slider)
        card = findViewById(R.id.card)

        setHeight()
        setWeight()

        supportActionBar?.title = getString(R.string.title)

        runnableSum = object : Runnable {
            override fun run() {
                weight++
                setWeight()
                handler.postDelayed(this, 100)
            }
        }

        runnableRest = object : Runnable {
            override fun run() {
                weight--
                setWeight()
                handler.postDelayed(this, 100)
            }
        }

        clickButton(runnableSum, addButton)
        clickButton(runnableRest, restButton)

        calculateButton.setOnClickListener {
            height = heightText.text.toString().toInt()
            val calc = weight / (height / 100f).pow(2)
            result.text = "%.2f".format(calc)
            setResultClassification()
        }

        slider.addOnChangeListener { _, value, _ ->
            height = value.toInt()
            setHeight()
        }
//TODO change slide when the value is entered in the heightText
//        heightText.addTextChangedListener(object: TextWatcher {
//
//                  override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//                //TODO("Not yet implemented")
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                //TODO("Not yet implemented")
//            }
//
//            override fun afterTextChanged(s: Editable?) {
//                var text = s.toString().toFloat()
//                while (text in 100f .. 300f) {
//                        slider.value = text
//                }
//            }
//        })
    }

    @SuppressLint("ClickableViewAccessibility")
    fun clickButton(runnable: Runnable, button: Button) {
        button.setOnTouchListener { _, event ->
            when (event.action) {
                android.view.MotionEvent.ACTION_DOWN -> {
                    handler.post(runnable)
                    true
                }
                android.view.MotionEvent.ACTION_UP, android.view.MotionEvent.ACTION_CANCEL -> {
                    handler.removeCallbacks(runnable)
                    true
                }
                else -> false
            }
        }
    }

    fun setResultClassification() {
        //can be used "when" and not "if"
        val resultNum = result.text.toString().toFloat()
        if (resultNum < 18.5) {
            resultClassification.setText(R.string.result_classification_low)
            card.setCardBackgroundColor(getColor(R.color.yellow))
        } else if (resultNum in 18.5..24.9) {
            resultClassification.setText(R.string.result_classification_normal)
            card.setCardBackgroundColor(getColor(R.color.green))
        } else if (resultNum in 24.9..29.9) {
            resultClassification.setText(R.string.result_classification_height)
            card.setCardBackgroundColor(getColor(R.color.magenta))
        } else if (resultNum > 29.9) {
            resultClassification.setText(R.string.result_classification_very_height)
            card.setCardBackgroundColor(getColor(R.color.red))
        } else {
            resultClassification.text = ""
        }
    }

    fun setHeight() {
        heightText.setText(height.toString())
        slider.value = height.toFloat()
    }

    fun setWeight() {
        weightText.text = getString(R.string.kg_format, weight)
    }
}