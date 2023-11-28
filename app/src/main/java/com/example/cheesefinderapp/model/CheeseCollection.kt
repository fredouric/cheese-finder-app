package com.example.cheesefinderapp.model

open class CheeseCollection() {
    private val cheeseCollection : ArrayList<Cheese> = ArrayList()

    fun getAllCheeses() : List<Cheese>{
        return this.cheeseCollection.sortedBy { it.fromage }
    }

    fun addCheese(cheese: Cheese) {
        if (!this.cheeseCollection.contains(cheese))
            this.cheeseCollection.add(cheese)
    }

    fun getArrayList(): ArrayList<Cheese> {
        return this.cheeseCollection
    }
}