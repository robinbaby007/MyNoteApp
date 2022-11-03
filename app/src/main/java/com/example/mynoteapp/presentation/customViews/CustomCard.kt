package com.example.mynoteapp.presentation.customViews

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mynoteapp.R

@Composable
fun CustomLoginCard(
    shape: Shape,
    modifier: Modifier = Modifier,
    click: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxSize()
            .padding(30.dp)
            .clickable {
              click()
            },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        shape = shape
    ) {

        Column(modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
            Box(
                modifier.padding(20.dp).border(width = 2.dp, color = Color.Gray , shape = RoundedCornerShape(10.dp)),
            ) {
                Row(
                    modifier.fillMaxWidth().padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_google),
                        contentDescription = "google icon",
                        modifier.size(30.dp)
                    )
                    Text(
                        text = LocalContext.current.getString(R.string.login_with_google),
                        style = MaterialTheme.typography.titleMedium
                    )
                }

            }

        }
    }

}


@Preview
@Composable
fun Prev() {
    CustomLoginCard(MaterialTheme.shapes.large) {}
}
