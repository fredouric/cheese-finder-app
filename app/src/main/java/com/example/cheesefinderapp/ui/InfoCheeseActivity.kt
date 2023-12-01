package com.example.cheesefinderapp.ui

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.cheesefinderapp.R
import com.example.cheesefinderapp.model.Cheese
import com.google.android.material.floatingactionbutton.FloatingActionButton

class InfoCheeseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_cheese)

        val cheese: Cheese? = intent.getSerializableExtra("cheese") as Cheese?
        val btnHome = findViewById<FloatingActionButton>(R.id.aic_home_button)

        setInfoCheese(cheese)

        btnHome.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setInfoCheese(cheese: Cheese?){
        val cheeseNameTextView: TextView = findViewById(R.id.aic_cheese_name)
        val cheeseLaitTextView: TextView = findViewById(R.id.aic_cheese_milk)
        val cheeseDepartementTextView: TextView = findViewById(R.id.aic_cheese_department)

        cheeseNameTextView.text = cheese?.fromage
        cheeseLaitTextView.text = cheese?.lait?.joinToString(", ")
        cheeseDepartementTextView.text = cheese?.departement
    }
}