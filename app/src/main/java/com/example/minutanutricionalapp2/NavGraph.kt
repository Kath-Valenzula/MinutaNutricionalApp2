package com.example.minutanutricionalapp2

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

sealed class Screen(val route: String) {
    object Login    : Screen("login")
    object Register : Screen("register")
    object Recover  : Screen("recover")
    object Minuta   : Screen("minuta")
    object Detail   : Screen("detail/{title}/{tips}")
}

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(Screen.Login.route)    { LoginScreen(navController) }
        composable(Screen.Register.route) { RegisterScreen(navController) }
        composable(Screen.Recover.route)  { RecoverScreen(navController) }
        composable(Screen.Minuta.route)   { MinutaScreen(navController) }

        // detalle
        composable(
            route = Screen.Detail.route,
            arguments = listOf(
                navArgument("title") { type = NavType.StringType },
                navArgument("tips")  { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val title = backStackEntry.arguments?.getString("title")
            val tips  = backStackEntry.arguments?.getString("tips")
            DetailScreen(navController, title, tips)
        }
    }
}
