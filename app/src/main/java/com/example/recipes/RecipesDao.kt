package com.example.recipes

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RecipesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(recipes: List<Recipe>)

    @Query("Select * from Recipes")
    fun getAll(): List<Recipe>

    @Query("Select * from Recipes Where idIndex = :idIndex")
    fun getRecipe(idIndex:Int): Recipe
}