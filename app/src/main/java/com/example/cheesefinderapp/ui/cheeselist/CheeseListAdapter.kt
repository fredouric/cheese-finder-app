package com.example.cheesefinderapp.ui.cheeselist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cheesefinderapp.R
import com.example.cheesefinderapp.model.Cheese

class CheeseListAdapter(
    private var cheeseCollection: ArrayList<Cheese>,
    private val onItemClick: (Cheese) -> Unit,
    private val onActionButtonClick: (String) -> Unit

) :

    RecyclerView.Adapter<CheeseListViewHolder>() {

    private var originalCheeseList: ArrayList<Cheese> = ArrayList(cheeseCollection)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheeseListViewHolder {
        val raw = LayoutInflater.from(parent.context).inflate(R.layout.raw_cheese, parent, false)
        return CheeseListViewHolder(raw, onItemClick, onActionButtonClick)
    }

    override fun getItemCount(): Int {
        return this.cheeseCollection.size
    }

    fun restoreData() {
        cheeseCollection.clear()
        cheeseCollection.addAll(originalCheeseList)
        notifyDataSetChanged()
    }

    fun updateData(newList: List<Cheese>) {
        cheeseCollection.clear()
        cheeseCollection.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: CheeseListViewHolder, position: Int) {
        val (id, departement, fromage, listLait, geo_shape, geo_point_2d, favorite) = this.cheeseCollection[position]
        val laitString = this.cheeseCollection[position].lait.joinToString(", ")

        holder.cheeseNom.text = fromage
        holder.cheeseLait.text = laitString
        holder.cheeseDepartement.text = departement

        if (favorite)
            holder.favoriteActionButton.setImageResource(R.drawable.baseline_favorite_24)
        else
            holder.favoriteActionButton.setImageResource(R.drawable.baseline_favorite_border_24)

        holder.itemView.setOnClickListener {
            onItemClick(this.cheeseCollection[position])
        }

        holder.favoriteActionButton.setOnClickListener {
            onActionButtonClick(this.cheeseCollection[position].id)

            if (favorite)
                holder.favoriteActionButton.setImageResource(R.drawable.baseline_favorite_24)
            else
                holder.favoriteActionButton.setImageResource(R.drawable.baseline_favorite_border_24)
        }
    }
}