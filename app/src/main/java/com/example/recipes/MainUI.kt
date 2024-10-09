package com.example.recipes

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter

@Composable
fun MainUI(data: NetworkResult, navController: NavController){
    Greeting(
        data = data,
        navController=navController
    )
}

@Composable
fun Greeting(data: NetworkResult, navController: NavController, modifier: Modifier = Modifier) {
    if (data.data.data == null) {
        Text(
            modifier = modifier,
            text = data.data.data ?: "Please Wait..."
        )
    } else {
        LazyColumn {
            items(data.itemsList) { item ->
                Row(verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(12.dp)
                        .clickable(true) {
                            navController.navigate("ItemDetails/"+item.idIndex)
                        }) {
                    Image(
                        painter = rememberAsyncImagePainter(item.thumb),
                        contentDescription = null,
                        modifier = Modifier.size(128.dp)
                    )
                    Column(Modifier.padding(horizontal = 6.dp)) {
                        Text(text = item.name, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        Text(text = stringResource(R.string.fats, item.fats))
                        Text(text = stringResource(R.string.calories, item.calories))
                        Text(text = stringResource(R.string.carbs, item.carbos))

                    }
                }
            }
        }
    }
}