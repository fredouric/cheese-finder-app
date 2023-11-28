package com.example.cheesefinderapp

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView

class CheeseAdapter (private var cheeseCollection: ArrayList<Cheese>) : RecyclerView.Adapter<CheeseViewHolder>()  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheeseViewHolder {
        val raw = LayoutInflater.from(parent.context).inflate(R.layout.raw_cheese, parent, false)
        return CheeseViewHolder(raw)
    }

    override fun getItemCount(): Int {
        return this.cheeseCollection.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: CheeseViewHolder, position: Int) {
        val (departement, fromage) = this.cheeseCollection[position]
        val laitString = this.cheeseCollection[position].lait.joinToString(", ")

        holder.cheeseNom.text = fromage
        holder.cheeseLait.text = laitString
        holder.cheeseDepartement.text = departement

    }
}