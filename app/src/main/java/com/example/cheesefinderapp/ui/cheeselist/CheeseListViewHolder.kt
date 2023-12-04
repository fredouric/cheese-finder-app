package com.example.cheesefinderapp.ui.cheeselist

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cheesefinderapp.R
import com.example.cheesefinderapp.model.Cheese
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CheeseListViewHolder(
    rootView: View,
    onItemClick: (Cheese) -> Unit,
    onActionButtonClick: (String) -> Unit
) : RecyclerView.ViewHolder(rootView) {
    var cheeseDepartement = rootView.findViewById<TextView>(R.id.r_cheese_departement)
    var cheeseNom = rootView.findViewById<TextView>(R.id.r_cheese_nom)
    var cheeseLait = rootView.findViewById<TextView>(R.id.r_cheese_lait)
    var favoriteActionButton =
        rootView.findViewById<FloatingActionButton>(R.id.r_cheese_favorite_button)
}
