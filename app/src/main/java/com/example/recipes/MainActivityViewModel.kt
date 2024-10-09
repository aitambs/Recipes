package com.example.recipes

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

data class NetworkResult(val data: ApiResponse, var itemsList: List<Recipe>)


class MainActivityViewModel: ViewModel() {
    private val json = Json { ignoreUnknownKeys = true }
    private val mutableState = MutableStateFlow(NetworkResult(ApiResponse(),emptyList()))
    val state:StateFlow<NetworkResult> = mutableState.asStateFlow()
    private val mutableState2 = MutableStateFlow<Recipe?>(null)
    val state2: StateFlow<Recipe?> = mutableState2.asStateFlow()
    fun fromNetwork(context: Context){
        viewModelScope.launch(Dispatchers.IO){
            val data = Api.get().getURL(MainActivity.url)
            var itemsList: List<Recipe> = emptyList()
            if (data.data!=null){
                itemsList = json.decodeFromString<List<Recipe>>(data.data)
                RecipesDatabase.get(context).dao().insertAll(itemsList)
                itemsList = RecipesDatabase.get(context).dao().getAll()
            }
            mutableState.value= NetworkResult(data,itemsList)
        }
    }

    fun fromDatabase(context: Context, idIndex: Int){
        viewModelScope.launch(Dispatchers.IO){
            Log.e("VM", "id is: $idIndex")
            mutableState2.value=RecipesDatabase.get(context).dao().getRecipe(idIndex)
        }
    }
}
