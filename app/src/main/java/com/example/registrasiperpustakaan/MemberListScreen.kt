package com.example.registrasiperpustakaan

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemberListScreen(
    viewModel: MemberViewModel,
    onNavigateToRegistration: () -> Unit,
    onNavigateToDetail: (String) -> Unit
) {
    // PERUBAHAN PENTING: Mengumpulkan data dari Flow menjadi State Compose
    val memberList by viewModel.memberList.collectAsState()

    // State untuk menyimpan teks pencarian
    var searchQuery by remember { mutableStateOf("") }

    // Memfilter daftar berdasarkan nama atau nomor induk yang diketik
    val filteredList = memberList.filter {
        it.namaLengkap.contains(searchQuery, ignoreCase = true) ||
                it.nomorInduk.contains(searchQuery, ignoreCase = true)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Daftar Anggota") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToRegistration) {
                Text("+", style = MaterialTheme.typography.titleLarge)
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            // Kolom Pencarian
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Cari Nama atau NIM/NIP...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            )

            // Menampilkan list atau pesan kosong
            if (filteredList.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
                    Text(
                        text = if (memberList.isEmpty()) "Belum ada anggota yang terdaftar." else "Data tidak ditemukan.",
                        modifier = Modifier.padding(top = 32.dp)
                    )
                }
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(filteredList) { member ->
                        MemberCard(
                            member = member,
                            onDeleteClick = { viewModel.deleteMember(member) },
                            onDetailClick = { onNavigateToDetail(member.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MemberCard(member: Member, onDeleteClick: () -> Unit, onDetailClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(text = member.namaLengkap, fontWeight = FontWeight.Bold)
                    Text(text = "NIM: ${member.nomorInduk}", style = MaterialTheme.typography.bodyMedium)
                    Text(text = member.jenisKeanggotaan, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.secondary)
                }
            }

            // Baris Tombol Aksi (Detail & Edit, Hapus)
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = onDetailClick) {
                    Text("Detail & Edit", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                }
                TextButton(onClick = onDeleteClick) {
                    Text("Hapus", color = MaterialTheme.colorScheme.error, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}