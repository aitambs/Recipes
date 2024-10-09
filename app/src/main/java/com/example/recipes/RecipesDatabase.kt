package com.example.recipes

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Recipe::class], version = 1, exportSchema = false)
abstract class RecipesDatabase: RoomDatabase() {
    companion object{
        var instance: RecipesDatabase?=null
        fun get(context:Context): RecipesDatabase{
            synchronized(RecipesDatabase::class.java){
                if (instance == null) {
                    instance=Room.databaseBuilder(context, RecipesDatabase::class.java, "recipes-db").build()
                }
            }
            return instance!!
        }
    }
    abstract fun dao(): RecipesDao
}