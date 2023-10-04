package uvg.edu.gt.lab04.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import uvg.edu.gt.lab04.Screen
import uvg.edu.gt.lab04.data.remote.MemesService
import uvg.edu.gt.lab04.data.remote.dto.Meme

@Composable
fun HomeView(navController: NavController) {
    var (memes, setMemes) = remember { mutableStateOf(emptyList<Meme>()) }
    LaunchedEffect(key1 = Unit) {
        val fetchedCities : List<Meme> = getMemes()
        setMemes(fetchedCities)
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Select a Template",
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(modifier = Modifier.fillMaxWidth()){
            items(memes) {meme ->
                memeCard(navController, meme)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun memeCard(navController: NavController, meme : Meme){
    Card (modifier = Modifier.fillMaxWidth(),
        onClick = {
            navController.navigate(Screen.GenerateMemeView.route + "/${meme.id}/${meme.name}")
        }){
        AsyncImage(model = meme.url,
            contentDescription = "Meme Template",
            contentScale = ContentScale.Crop
        )
        Text(text = meme.name,
            style = MaterialTheme.typography.displaySmall)
    }
}

suspend fun getMemes() : List<Meme>{
    var memes : List<Meme>;

    val client = HttpClient(CIO) {
        install(Logging) {
            logger = Logger.DEFAULT
            filter { request ->
                request.url.host.contains("ktor.io")
            }
        }
    }

    val memesServices : MemesService = MemesService(client)

    return memesServices.getMemes()
}