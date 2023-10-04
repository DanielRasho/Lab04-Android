package uvg.edu.gt.lab04

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import uvg.edu.gt.lab04.ui.theme.Lab04Theme
import uvg.edu.gt.lab04.views.GenerateMemeView
import uvg.edu.gt.lab04.views.HomeView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lab04Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = Screen.HomeView.route){
                        composable(route = Screen.HomeView.route){
                            HomeView(navController)
                        }
                        composable(
                            route = Screen.GenerateMemeView.route + "/{templateID}/{templateName}",
                            arguments = listOf(
                                navArgument("templateID") { type = NavType.StringType },
                                navArgument("templateName") { type = NavType.StringType })
                        ){ backStackEntry ->
                            val templateID = backStackEntry.arguments?.getString("templateID")
                            val templateName = backStackEntry.arguments?.getString("templateName")
                            requireNotNull(templateID) { Log.e("Error","templateID must NOT be null")}
                            requireNotNull(templateName) { Log.e("Error","templateName must NOT be null")}
                            GenerateMemeView(navController, templateID, templateName)
                        }
                    }
                }
            }
        }
    }
}

suspend fun getMemes(){

}