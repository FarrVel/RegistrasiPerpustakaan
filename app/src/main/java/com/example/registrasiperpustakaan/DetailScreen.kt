package com.example.registrasiperpustakaan

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DetailScreen(
    memberId: String,
    viewModel: MemberViewModel,
    onNavigateBack: () -> Unit,
    onNavigateToEdit: (String) -> Unit
) {
    val memberList by viewModel.memberList.collectAsState()
    val member = memberList.find { it.id == memberId }

    val navyBlue = Color(0xFF0A2E65)
    val accentBlue = Color(0xFF1554D1)
    val bgLight = Color(0xFFF4F6FA)
    val redDelete = Color(0xFFC62828)

    if (member == null) {
        LaunchedEffect(Unit) { onNavigateBack() }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bgLight)
            .verticalScroll(rememberScrollState())
    ) {
        // Header Navy Blue
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(navyBlue)
                .padding(top = 48.dp, bottom = 48.dp, start = 20.dp, end = 20.dp)
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = onNavigateBack, modifier = Modifier.size(32.dp)) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Kembali", tint = Color.White)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Detail Anggota",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Profil Singkat dengan Avatar Inisial
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(68.dp)
                            .clip(CircleShape)
                            .background(Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = getInitials(member.namaLengkap),
                            color = accentBlue,
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = member.namaLengkap,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = member.nomorInduk,
                            color = Color.White.copy(alpha = 0.85f),
                            fontSize = 14.sp
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Surface(
                            color = accentBlue,
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = member.jenisKeanggotaan,
                                color = Color.White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                            )
                        }
                    }
                }
            }
        }

        // Kartu Putih Berisi Detail Data
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .offset(y = (-24).dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                DetailRowItem("Nama Lengkap", member.namaLengkap)
                DetailRowItem("NIM / NIP", member.nomorInduk)
                DetailRowItem("Program Studi / Unit", member.programStudi)
                DetailRowItem("Alamat", member.alamat)
                DetailRowItem("Nomor Telepon", member.nomorTelepon)
                DetailRowItem("Email", member.email, showDivider = false)
            }
        }

        // Dua Tombol di Bawah: Edit Data & Hapus
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .offset(y = (-8).dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = { onNavigateToEdit(member.id) },
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = accentBlue)
            ) {
                Text("Edit Data", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Color.White)
            }

            OutlinedButton(
                onClick = {
                    viewModel.deleteMember(member)
                    onNavigateBack()
                },
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, Color(0xFFFFCDD2)),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = redDelete)
            ) {
                Text("Hapus", fontWeight = FontWeight.Bold, fontSize = 15.sp)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun DetailRowItem(label: String, value: String, showDivider: Boolean = true) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = label, color = Color(0xFF6E7781), fontSize = 12.sp)
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            color = Color(0xFF1F2328),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
        if (showDivider) {
            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = Color(0xFFEEF1F4), thickness = 1.dp)
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}