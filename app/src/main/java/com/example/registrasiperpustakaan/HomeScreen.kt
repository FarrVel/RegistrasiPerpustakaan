package com.example.registrasiperpustakaan

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen(
    onNavigateToRegistration: () -> Unit,
    onNavigateToList: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center // Posisi di tengah layar
    ) {
        // 1. Header: Logo (Menggunakan Emoji Buku agar aman)
        Text(
            text = "📚",
            fontSize = 80.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // 2. Teks Judul Besar
        Text(
            text = "LibraryReg",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        // 3. Deskripsi Singkat
        Text(
            text = "Sistem Pendaftaran Anggota Perpustakaan",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 8.dp, bottom = 48.dp),
            color = MaterialTheme.colorScheme.secondary
        )

        // 4. Kontrol Utama: Tombol Pendaftaran Anggota
        Button(
            onClick = onNavigateToRegistration,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp), // Dibuat ukuran besar agar mudah diklik
            shape = MaterialTheme.shapes.medium
        ) {
            Text("Pendaftaran Anggota", fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(16.dp)) // Jarak antar tombol

        // 5. Kontrol Utama: Tombol Daftar Anggota (Desain Outlined agar berbeda)
        OutlinedButton(
            onClick = onNavigateToList,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = MaterialTheme.shapes.medium
        ) {
            Text("Daftar Anggota", fontSize = 18.sp)
        }
    }
}