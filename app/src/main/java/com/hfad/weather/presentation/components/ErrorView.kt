package com.hfad.weather.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hfad.weather.ui.theme.Pink40
import com.hfad.weather.ui.theme.Pink80
import org.intellij.lang.annotations.JdkConstants

@Composable
fun ErrorView (text: String){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .alpha(0.7f)
            .padding(top = 25.dp),
        colors = CardDefaults.cardColors(containerColor = Pink80, contentColor = Pink40),
        elevation = CardDefaults.cardElevation(5.dp),
        shape = RoundedCornerShape(50.dp),
    ){
        Text(
            modifier = Modifier.padding(top = 8.dp, start = 8.dp),
            text = text,
            textAlign = TextAlign.Center,
            color = Color.Red
            )
    }
}