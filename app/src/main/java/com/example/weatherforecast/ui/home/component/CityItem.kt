package com.example.weatherforecast.ui.home.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherforecast.data.model.City

/*
this replace CityAdapter ViewHolder
*/
@Composable
fun CityItem(

    city: City,

    onClick: () -> Unit,

    modifier: Modifier = Modifier

) {

    Card(

        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }

    ) {

        Column(
            modifier = Modifier
                .padding(16.dp)

        ) {

            Text(

                text = city.name ?: "",

                fontSize = 18.sp,

                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            Text(
                text = city.state ?: ""
            )

            Text(
                text = city.country ?: ""
            )
        }
    }
}
