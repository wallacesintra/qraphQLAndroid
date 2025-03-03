package com.example.graphqlandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.graphqlandroid.domain.viewmodels.authentication.LoginViewModel
import com.example.graphqlandroid.presentation.FirstScreen
import com.example.graphqlandroid.presentation.navigation.Navigation
import com.example.graphqlandroid.ui.theme.GraphQLAndroidTheme
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinContext

lateinit var navController: NavHostController


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            KoinContext {
                GraphQLAndroidTheme {
                    navController = rememberNavController()
                    val loginViewModel = koinViewModel<LoginViewModel>()


                    Navigation(
                        navController = navController
                    )

                }

            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GraphQLAndroidTheme {
        Greeting("Android")
    }
}