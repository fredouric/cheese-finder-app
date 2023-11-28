package com.example.cheesefinderapp.ui.cheeselist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cheesefinderapp.R
import com.example.cheesefinderapp.model.Cheese

class CheeseListAdapter(private var cheeseCollection: ArrayList<Cheese>) :

    RecyclerView.Adapter<CheeseListViewHolder>() {

    private var originalCheeseList: ArrayList<Cheese> = ArrayList(cheeseCollection)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheeseListViewHolder {
        val raw = LayoutInflater.from(parent.context).inflate(R.layout.raw_cheese, parent, false)
        return CheeseListViewHolder(raw)
    }

    override fun getItemCount(): Int {
        return this.cheeseCollection.size
    }

    fun restoreData() {
        cheeseCollection.clear()
        cheeseCollection.addAll(originalCheeseList)
        notifyDataSetChanged()
    }

    fun updateData(newList: ArrayList<Cheese>) {
        cheeseCollection.clear()
        cheeseCollection.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: CheeseListViewHolder, position: Int) {
        val (departement, fromage) = this.cheeseCollection[position]
        val laitString = this.cheeseCollection[position].lait.joinToString(", ")

        holder.cheeseNom.text = fromage
        holder.cheeseLait.text = laitString
        holder.cheeseDepartement.text = departement

    }
}