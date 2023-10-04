package uvg.edu.gt.lab04.views

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import uvg.edu.gt.lab04.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenerateMemeView(navController: NavController, templateID : String){
    var topText by remember { mutableStateOf("") }
    var bottomText by remember { mutableStateOf("") }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = templateID)
        OutlinedTextField(
            value = topText,
            onValueChange = { topText = it },
            label = { Text(text = "Top Text") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = bottomText,
            onValueChange = { bottomText = it },
            label = { Text(text = "Bottom Text") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {

            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(5.dp)
        ) {
            Text(text = "Generate!")
        }
    }
}

suspend fun owo(): String{
    return ""
}