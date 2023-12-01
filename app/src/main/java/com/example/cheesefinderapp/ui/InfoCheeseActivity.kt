package com.example.cheesefinderapp.ui

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.cheesefinderapp.R
import com.example.cheesefinderapp.model.Cheese

class InfoCheeseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_cheese)

        val cheese: Cheese? = intent.getSerializableExtra("cheese") as Cheese?

        // Use the cheese data to populate UI elements
        val cheeseNameTextView: TextView = findViewById(R.id.aic_cheese_name)
        val cheeseLaitTextView: TextView = findViewById(R.id.aic_cheese_milk)
        val cheeseDepartementTextView: TextView = findViewById(R.id.aic_cheese_department)

        cheeseNameTextView.text = cheese?.fromage
        cheeseLaitTextView.text = cheese?.lait?.joinToString(", ")
        cheeseDepartementTextView.text = cheese?.departement
    }

}