package com.example.registrasiperpustakaan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.registrasiperpustakaan.ui.theme.RegistrasiPerpustakaanTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RegistrasiPerpustakaanTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LibraryApp()
                }
            }
        }
    }
}

@Composable
fun LibraryApp() {
    val navController = rememberNavController()
    val memberViewModel: MemberViewModel = viewModel()

    // UBAH: startDestination sekarang adalah "home"
    NavHost(navController = navController, startDestination = "home") {

        // Layar 0: Beranda Utama (Menu)
        composable("home") {
            HomeScreen(
                onNavigateToRegistration = { navController.navigate("registration") },
                onNavigateToList = { navController.navigate("member_list") }
            )
        }

        // Layar 1: Formulir Pendaftaran
        composable("registration") {
            RegistrationScreen(
                viewModel = memberViewModel,
                onNavigateToList = {
                    navController.navigate("member_list") {
                        popUpTo("home")
                    }
                },
                onNavigateBack = {
                    // Perintah untuk mencabut layar saat ini dan kembali ke layar sebelumnya
                    navController.popBackStack()
                }
            )
        }

        // Layar 2: Daftar Anggota
        composable("member_list") {
            MemberListScreen(
                viewModel = memberViewModel,
                onNavigateToRegistration = {
                    navController.navigate("registration")
                },
                onNavigateToDetail = { memberId ->
                    navController.navigate("edit/$memberId")
                }
            )
        }

        // Layar 3: Detail & Edit Anggota
        composable(
            route = "edit/{memberId}",
            arguments = listOf(navArgument("memberId") { type = NavType.StringType })
        ) { backStackEntry ->
            val memberId = backStackEntry.arguments?.getString("memberId") ?: ""
            EditScreen(
                memberId = memberId,
                viewModel = memberViewModel,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}