package com.example.registrasiperpustakaan

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Fungsi pembantu untuk mengambil 2 huruf inisial nama (Contoh: Andi Pratama -> AP)
fun getInitials(name: String): String {
    val parts = name.trim().split("\\s+".toRegex()).filter { it.isNotEmpty() }
    return when {
        parts.isEmpty() -> "?"
        parts.size == 1 -> parts[0].take(2).uppercase()
        else -> "${parts[0].first()}${parts[1].first()}".uppercase()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemberListScreen(
    viewModel: MemberViewModel,
    onNavigateToRegistration: () -> Unit,
    onNavigateToDetail: (String) -> Unit,
    onNavigateToEdit: (String) -> Unit
) {
    val memberList by viewModel.memberList.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    val filteredList = memberList.filter {
        it.namaLengkap.contains(searchQuery, ignoreCase = true) ||
                it.nomorInduk.contains(searchQuery, ignoreCase = true)
    }

    val navyBlue = Color(0xFF0A2E65)
    val accentBlue = Color(0xFF1554D1)
    val bgLight = Color(0xFFF4F6FA)

    Scaffold(
        containerColor = bgLight,
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onNavigateToRegistration,
                containerColor = accentBlue,
                contentColor = Color.White,
                shape = RoundedCornerShape(50),
                modifier = Modifier.padding(bottom = 8.dp, end = 8.dp)
            ) {
                Text(
                    text = "+ Tambah Anggota",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = innerPadding.calculateBottomPadding())
        ) {
            // Header Navy Blue & Search Bar Melayang
            Box(modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(navyBlue)
                        .padding(top = 48.dp, start = 20.dp, end = 20.dp, bottom = 42.dp)
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = Color.White,
                                modifier = Modifier.size(36.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = Icons.Default.MenuBook,
                                        contentDescription = "Logo",
                                        tint = navyBlue,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "LibraryReg",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Daftar Anggota",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 26.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Kelola data anggota perpustakaan",
                            color = Color.White.copy(alpha = 0.8f),
                            fontSize = 14.sp
                        )
                    }
                }

                // Kotak Pencarian Putih Melayang
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .align(Alignment.BottomCenter)
                        .offset(y = 24.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = { Text("Cari nama atau nomor induk...", color = Color.Gray) },
                        leadingIcon = {
                            Icon(Icons.Default.Search, contentDescription = "Cari", tint = Color.Gray)
                        },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent,
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Spacer(modifier = Modifier.height(36.dp))

            // Daftar Kartu Anggota
            if (filteredList.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
                    Text(
                        text = if (memberList.isEmpty()) "Belum ada anggota yang terdaftar." else "Data tidak ditemukan.",
                        color = Color.Gray,
                        modifier = Modifier.padding(top = 40.dp)
                    )
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(start = 20.dp, end = 20.dp, bottom = 90.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(filteredList) { member ->
                        MemberCard(
                            member = member,
                            onCardClick = { onNavigateToDetail(member.id) },
                            onEditClick = { onNavigateToEdit(member.id) },
                            onDeleteClick = { viewModel.deleteMember(member) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MemberCard(
    member: Member,
    onCardClick: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    val accentBlue = Color(0xFF1554D1)
    val lightBlueBg = Color(0xFFEAF1FF)
    val redDelete = Color(0xFFC62828)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCardClick() }, // Klik kartu untuk lihat Detail Anggota
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        border = BorderStroke(1.dp, Color(0xFFE5EAF2))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Lingkaran Inisial Nama (Avatar)
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(lightBlueBg),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = getInitials(member.namaLengkap),
                    color = accentBlue,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            // Informasi Nama, NIM, dan Badge Keanggotaan
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = member.namaLengkap,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color(0xFF1E293B)
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = member.nomorInduk,
                    fontSize = 13.sp,
                    color = Color(0xFF64748B)
                )
                Spacer(modifier = Modifier.height(6.dp))
                Surface(
                    color = lightBlueBg,
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = member.jenisKeanggotaan,
                        color = accentBlue,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }
            }

            // Dua Tombol Ikon: Edit (Biru) & Hapus (Merah)
            Row {
                IconButton(onClick = onEditClick) {
                    Icon(
                        imageVector = Icons.Outlined.Edit,
                        contentDescription = "Edit",
                        tint = accentBlue
                    )
                }
                IconButton(onClick = onDeleteClick) {
                    Icon(
                        imageVector = Icons.Outlined.Delete,
                        contentDescription = "Hapus",
                        tint = redDelete
                    )
                }
            }
        }
    }
}