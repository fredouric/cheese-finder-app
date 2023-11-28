package com.example.cheesefinderapp.model

class CheeseCollection() {
    private val cheeseCollection: ArrayList<Cheese> = ArrayList()

    fun addCheese(cheese: Cheese) {
        if (!this.cheeseCollection.contains(cheese))
            this.cheeseCollection.add(cheese)
    }

    fun getArrayList(): ArrayList<Cheese> {
        return this.cheeseCollection
    }
}