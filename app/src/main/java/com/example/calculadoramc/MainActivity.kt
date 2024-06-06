package com.example.calculadoramc

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.slider.Slider
import kotlin.math.pow

class MainActivity : AppCompatActivity() {

    lateinit var hightText: EditText
    lateinit var weightText: TextView
    lateinit var addButton: Button
    lateinit var restButton: Button
    lateinit var calculateButton: Button
    lateinit var result: TextView
    lateinit var resultClassification: TextView
    lateinit var slider: Slider

    val handler = Handler(Looper.getMainLooper())
    lateinit var runnableSum: Runnable
    lateinit var runnableRest: Runnable

    var hight = 150
    var weight = 60

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        hightText = findViewById(R.id.valueHeight)
        weightText = findViewById(R.id.valueWeight)
        addButton = findViewById(R.id.addButton)
        restButton = findViewById(R.id.restButton)
        calculateButton = findViewById(R.id.calculate)
        result = findViewById(R.id.result)
        resultClassification = findViewById(R.id.result_classification)
        slider = findViewById(R.id.slider)

        setHeight()
        setWeight()

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
            hight = hightText.text.toString().toInt()
            val calc = weight / (hight / 100f).pow(2)
            result.text = calc.toString().format("%.${2}f")
            setResultClassification()
        }

        slider.addOnChangeListener { _, value, _ ->
            hight = value.toInt()
            setHeight()
        }
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
        val resultNum = result.text.toString().toFloat()
        if (resultNum < 18.5) {
            resultClassification.setText(R.string.result_classification_low)
        } else if (resultNum in 18.5..24.9) {
            resultClassification.setText(R.string.result_classification_normal)
        } else if (resultNum in 25.0..29.9) {
            resultClassification.setText(R.string.result_classification_height)
        } else if (resultNum >= 30) {
            resultClassification.setText(R.string.result_classification_very_height)
        } else {
            resultClassification.text = ""
        }
    }

    fun setHeight() {
        hightText.setText(hight.toString())
        slider.value = hight.toFloat()
    }

    @SuppressLint("SetTextI18n")
    fun setWeight() {
        weightText.text = "$weight Kg"
    }
}