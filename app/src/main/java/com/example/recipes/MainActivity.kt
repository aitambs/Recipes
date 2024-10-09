package com.example.recipes

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.recipes.ui.theme.TestApplicationTheme


private const val MAIN_SCREEN = "MainScreen"
private const val DETAILS_SCREEN = "ItemDetails"

class MainActivity : ComponentActivity() {
    companion object{
        val url="https://hf-android-app.s3-eu-west-1.amazonaws.com/android-test/recipes.json"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            TestApplicationTheme {
                val navController = rememberNavController()
                val currentBackStack by navController.currentBackStackEntryAsState()
                val currentDestination = currentBackStack?.destination
                val viewModel = MainActivityViewModel()
//                ScaffoldDefaults.contentWindowInsets
                Scaffold { innerPadding ->
                    Box(Modifier.padding(innerPadding)){
                        var shouldLoadData=remember { mutableStateOf(true) }
                        val itemShown= remember { mutableStateOf(false) }
                        val data by viewModel.state.collectAsState()
                        val item by viewModel.state2.collectAsState()
                        NavHost(navController = navController, startDestination = MAIN_SCREEN,
                            modifier = Modifier.fillMaxSize().background(colorResource(R.color.purple_200))){
                            composable(route= MAIN_SCREEN){
                                if (currentDestination?.route==MAIN_SCREEN && shouldLoadData.value) {
                                    shouldLoadData=remember { mutableStateOf(false)}
                                    viewModel.fromNetwork(this@MainActivity.applicationContext)
                                }
                                MainUI(data, navController)
                            }
                            composable(route="ItemDetails/{idIndex}"){
                                val itemID=currentBackStack?.arguments?.getString("idIndex")
                                if (itemID!=null){
                                    Log.e("ID", ""+itemID)
                                    viewModel.fromDatabase(this@MainActivity.applicationContext, itemID.toInt())
                                }
                                DetailsUI(recipe = item)
                            }
                        }
                    }
                }
            }
        }
    }
}