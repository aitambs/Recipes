package com.example.recipes

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter

@Composable
fun DetailsUI(recipe: Recipe?){
    Scaffold(modifier = Modifier
        .fillMaxSize()) { innerPadding ->
        if (recipe!=null) {
            Column(Modifier.fillMaxSize().padding(innerPadding)
                ) {//.verticalScroll(rememberScrollState(1))
                Image(
                    painter = rememberAsyncImagePainter(recipe.image),
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth())
                Row (modifier = Modifier.fillMaxWidth(1f), horizontalArrangement = Arrangement.Center){
                    Text(text = recipe.name, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                }
                Column(Modifier.verticalScroll(rememberScrollState(1))){
                    Text(text = stringResource(R.string.fats, recipe.fats))
                    Text(text = stringResource(R.string.calories, recipe.calories))
                    Text(text = stringResource(R.string.carbs, recipe.carbos))
                    Text(text = recipe.description, fontSize = 18.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Justify)
                }
            }
        }
    }
}