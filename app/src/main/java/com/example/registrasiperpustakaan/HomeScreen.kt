package com.example.registrasiperpustakaan

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen(
    onNavigateToRegistration: () -> Unit,
    onNavigateToList: () -> Unit
) {
    val navyBlue = Color(0xFF0A2E65)
    val accentBlue = Color(0xFF1554D1)
    val bgLight = Color(0xFFF4F6FA)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bgLight)
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = navyBlue,
            modifier = Modifier.size(96.dp),
            shadowElevation = 6.dp
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = Icons.Default.MenuBook,
                    contentDescription = "Logo Perpustakaan",
                    tint = Color.White,
                    modifier = Modifier.size(48.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "LibraryReg",
            fontSize = 32.sp,
            fontWeight = FontWeight.ExtraBold,
            color = navyBlue
        )

        Text(
            text = "Sistem Pendaftaran Anggota Perpustakaan",
            fontSize = 15.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 8.dp, bottom = 48.dp),
            color = Color(0xFF64748B)
        )

        Button(
            onClick = onNavigateToRegistration,
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(containerColor = navyBlue)
        ) {
            Text("Pendaftaran Anggota", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }

        Spacer(modifier = Modifier.height(14.dp))

        OutlinedButton(
            onClick = onNavigateToList,
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            shape = RoundedCornerShape(14.dp),
            border = BorderStroke(1.5.dp, accentBlue),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = accentBlue)
        ) {
            Text("Daftar Anggota", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}