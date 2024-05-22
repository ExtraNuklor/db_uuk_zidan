import Components.EditTransaksiPage
import Components.MainPage
import Components.PetugasPage
import Components.TambahkanTransaksiPage
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
@Preview
fun App() {
    var text by remember { mutableStateOf("Hello, World!") }

    MaterialTheme {
        navigation()
    }
}

@Composable
fun navigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "MainPage"
    ) {
        composable(
            route = "MainPage"
        ) {
            MainPage(navController)
        }

        composable(
            route = "PetugasPage"
        ) {
            PetugasPage(navController)
        }

        composable(
            route = "TambahkanTransaksiPage"
        ) {
            TambahkanTransaksiPage(navController)
        }

        composable(
            route = "EditTransaksiPage"
        ) {
            EditTransaksiPage(navController)
        }

    }
}


fun main() = application {
    Window(title = "Aplikasi Kasir", onCloseRequest = ::exitApplication) {
        App()
    }
}
