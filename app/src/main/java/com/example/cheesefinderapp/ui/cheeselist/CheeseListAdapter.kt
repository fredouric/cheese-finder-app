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
        sortCheesesByFavoriteAndName()
        val raw = LayoutInflater.from(parent.context).inflate(R.layout.raw_cheese, parent, false)
        return CheeseListViewHolder(raw, onItemClick, onActionButtonClick)
    }

    override fun getItemCount(): Int {
        return this.cheeseCollection.size
    }

    private fun sortCheesesByFavoriteAndName() {
        cheeseCollection.sortWith(compareByDescending<Cheese> { it.favorite }.thenBy { it.fromage })
    }

    fun restoreData() {
        cheeseCollection.clear()
        cheeseCollection.addAll(originalCheeseList)
        sortCheesesByFavoriteAndName()
        notifyDataSetChanged()
    }

    fun updateData(newList: List<Cheese>) {
        cheeseCollection.clear()
        cheeseCollection.addAll(newList)
        sortCheesesByFavoriteAndName()
        notifyDataSetChanged()
    }

    private fun setFavoriteIcon(favorite: Boolean, cheeseListViewHolder: CheeseListViewHolder) {
        if (favorite)
            cheeseListViewHolder.favoriteActionButton.setIconResource(R.drawable.baseline_favorite_24)
        else
            cheeseListViewHolder.favoriteActionButton.setIconResource(R.drawable.baseline_favorite_border_24)
    }

    private fun setMilkIcon(lait: String, cheeseListViewHolder: CheeseListViewHolder) {
        when (lait) {
            "Vache" -> cheeseListViewHolder.iconLait.setImageResource(R.drawable.cow)
            "Vaches" -> cheeseListViewHolder.iconLait.setImageResource(R.drawable.cow)
            "Brebis" -> cheeseListViewHolder.iconLait.setImageResource(R.drawable.ewe)
            "Chèvre" -> cheeseListViewHolder.iconLait.setImageResource(R.drawable.goat)
            else -> cheeseListViewHolder.iconLait.setImageResource(R.drawable.cheese)
        }
    }

    override fun onBindViewHolder(cheeseListViewHolder: CheeseListViewHolder, position: Int) {
        val (id, departement, fromage, listLait, geo_shape, geo_point_2d, favorite) = this.cheeseCollection[position]
        val laitString = this.cheeseCollection[position].lait.joinToString(", ")

        cheeseListViewHolder.cheeseNom.text = fromage
        cheeseListViewHolder.cheeseLait.text = laitString
        cheeseListViewHolder.cheeseDepartement.text = departement

        setFavoriteIcon(favorite, cheeseListViewHolder)
        setMilkIcon(laitString, cheeseListViewHolder)

        cheeseListViewHolder.itemView.setOnClickListener {
            onItemClick(this.cheeseCollection[position])
        }

        cheeseListViewHolder.favoriteActionButton.setOnClickListener {
            onActionButtonClick(this.cheeseCollection[position].id)

            setFavoriteIcon(favorite, cheeseListViewHolder)
        }
    }
}