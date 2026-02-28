package com.example.fitfreak.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/*
Notes
 */

@Composable
fun  HeaderBar(){

    Box(modifier = Modifier.fillMaxWidth()
        .padding(vertical = 64.dp ),
        contentAlignment = Alignment.Center
    )
    {
        Text(text = "Menu")
    }



}

@Composable
fun Body(
    items : List<BodyItem>
    ,modifier: Modifier = Modifier
    ,onItemClick : (BodyItem) -> Unit
)

{
    LazyColumn(modifier = modifier){
        items(items){item ->

            Row (
                modifier = Modifier.fillMaxWidth()
                    .clickable {
                        onItemClick(item)
                    }
                    .padding(16.dp)


            ){

                Text(text = item.obj1)

                Spacer(modifier = Modifier.weight(1f))

                Text(text = item.obj2)

            }
        }
    }




}

data class BodyItem(
    val  obj1 : String
    ,val obj2: String
)