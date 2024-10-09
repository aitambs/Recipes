package com.example.recipes

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "Recipes", indices = [Index(value = ["name"],
    unique = true)])
data class Recipe(
    @PrimaryKey(autoGenerate = true) @kotlinx.serialization.Transient var idIndex: Int=0,
    val calories:String, val carbos: String, val description:String,
    val difficulty:Int, val fats:String,
    val headline:String, val id:String, val image:String, val name:String,
    val proteins:String, val thumb:String, val time:String)