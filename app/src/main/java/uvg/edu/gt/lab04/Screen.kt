package uvg.edu.gt.lab04

sealed class Screen (val route: String){
    object HomeView : Screen("Home")
    object GenerateMemeView : Screen("GenerateMeme")
}