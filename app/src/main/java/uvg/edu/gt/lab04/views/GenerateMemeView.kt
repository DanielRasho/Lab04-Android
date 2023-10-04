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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import uvg.edu.gt.lab04.R
import uvg.edu.gt.lab04.Screen
import uvg.edu.gt.lab04.data.remote.HttpRoutes
import uvg.edu.gt.lab04.data.remote.MemesService
import uvg.edu.gt.lab04.data.remote.dto.Meme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenerateMemeView(navController: NavController, templateID : String, templateName : String){
    val coroutineScope = rememberCoroutineScope()
    var templateUrl by remember { mutableStateOf("") }
    var topText by remember { mutableStateOf("") }
    var bottomText by remember { mutableStateOf("") }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = templateName,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
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
                coroutineScope.launch {
                    templateUrl = captionMeme(templateID, topText, bottomText)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(5.dp)
        ) {
            Text(text = "Generate!")
        }
        AsyncImage(model = templateUrl,
            contentDescription = "Meme Template",
            contentScale = ContentScale.Crop,
            placeholder = painterResource(R.drawable.file_not_found)
        )
    }
}

suspend fun captionMeme(templateID : String, topText : String, bottomText : String): String{

    val client = HttpClient(CIO) {
        install(Logging) {
            logger = Logger.DEFAULT
            filter { request ->
                request.url.host.contains("ktor.io")
            }
        }
    }

    val memesServices : MemesService = MemesService(client)

    return memesServices.getTemplateWithCaption(templateID, topText, bottomText)
}