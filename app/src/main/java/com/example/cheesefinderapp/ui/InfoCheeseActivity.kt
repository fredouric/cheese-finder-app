package com.example.cheesefinderapp.ui

import android.os.Bundle
import android.view.Menu
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.cheesefinderapp.R
import com.example.cheesefinderapp.model.Cheese

class InfoCheeseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_cheese)

        val cheese: Cheese? = intent.getSerializableExtra("cheese") as Cheese?

        setInfoCheese(cheese)

    }

    private fun setInfoCheese(cheese: Cheese?) {
        val cheeseNameTextView: TextView = findViewById(R.id.aic_cheese_name)
        val cheeseLaitTextView: TextView = findViewById(R.id.aic_cheese_milk)
        val cheeseDepartementTextView: TextView = findViewById(R.id.aic_cheese_department)

        cheeseNameTextView.text = "Nom du fromage : " + cheese?.fromage
        cheeseLaitTextView.text = "Lait : " + cheese?.lait?.joinToString(", ")
        cheeseDepartementTextView.text = "Département : " + cheese?.departement
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.go_back_menu, menu)
        val backBtn = menu.findItem(R.id.app_bar_back)

        backBtn.setOnMenuItemClickListener {
            onBackPressed()
            true
        }
        return true
    }
}
