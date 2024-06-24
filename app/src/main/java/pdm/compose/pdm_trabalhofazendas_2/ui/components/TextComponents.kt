package pdm.compose.pdm_trabalhofazendas_2.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TextTitle(
    text: String = "Empty",
    color: Color = Color.Black,
    fontSize: TextUnit = 32.sp
){
    Text(
        text = text,
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
        textAlign = TextAlign.Center,
        color = color, // Customize color as needed
        fontWeight = FontWeight.Bold,
        fontSize = fontSize
    )
}

@Composable
fun TextLabel(
    text: String = "Empty",
) {
    Text(
        text = text,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp),
        textAlign = TextAlign.Start,
        color = Color.Blue, // Customize color as needed
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    )
}