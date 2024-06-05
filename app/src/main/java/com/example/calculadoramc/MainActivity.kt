package com.example.calculadoramc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout

class MainActivity : AppCompatActivity() {

    lateinit var containerView : LinearLayout
    lateinit var button: Button
            override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

                containerView = findViewById(R.id.containerView)

                containerView.setBackgroundResource(R.color.red)

                button = findViewById(R.id.button)

                button.setOnClickListener { containerView.setBackgroundResource(com.example.calculadoramc.R.color.black) }
            }
}