package com.example.calculadoramc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kotlin.math.pow

class MainActivity : AppCompatActivity() {

    lateinit var hightText: EditText
    lateinit var weightText: TextView
    lateinit var addButton: Button
    lateinit var restButton: Button
    lateinit var calculateButton: Button
    lateinit var result: TextView
    lateinit var resultClassification: TextView

    var hight = 200
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

        setHeight()
        setWeight()

        addButton.setOnClickListener {
            weight++
            setWeight()
        }

        restButton.setOnClickListener {
            weight--
            setWeight()
        }

        calculateButton.setOnClickListener {
            hight = hightText.text.toString().toInt()
            val calc = weight / (hight / 100f).pow(2)
            result.text = calc.toString()
            serResultClassification()
        }
    }

    fun serResultClassification() {
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
    }

    fun setWeight() {
        weightText.text = "$weight Kg"
    }
}