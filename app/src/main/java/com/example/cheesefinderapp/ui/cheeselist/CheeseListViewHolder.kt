package com.example.cheesefinderapp.ui.cheeselist

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cheesefinderapp.R

class CheeseListViewHolder (rootView: View) : RecyclerView.ViewHolder(rootView){
    var cheeseDepartement = rootView.findViewById<TextView>(R.id.r_cheese_departement)
    var cheeseNom = rootView.findViewById<TextView>(R.id.r_cheese_nom)
    var cheeseLait =  rootView.findViewById<TextView>(R.id.r_cheese_lait)
}