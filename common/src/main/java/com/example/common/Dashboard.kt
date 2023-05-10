package com.example.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

//navController: NavController ,
@Composable
fun DashboardTabView(modifier: Modifier = Modifier, tabTitles: Array<String>, onclick: @Composable (Int) -> Unit){

   var tabIndex = rememberSaveable { mutableStateOf(0) }

   Column(modifier = modifier) {
      TabRow(selectedTabIndex = tabIndex.value, modifier = Modifier.fillMaxWidth()) {
         tabTitles.forEachIndexed { index, title ->
            Tab(text = { Text(title) },
               selected = tabIndex.value == index,
               onClick = {
                  tabIndex.value = index

               }, modifier = Modifier.background(Color.Red)
            )
         }
      }
         onclick(tabIndex.value)
   }


}

