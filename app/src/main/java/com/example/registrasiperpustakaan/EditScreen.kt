package com.example.registrasiperpustakaan

import android.util.Patterns
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditScreen(
    memberId: String,
    viewModel: MemberViewModel,
    onNavigateBack: () -> Unit
) {
    val memberList by viewModel.memberList.collectAsState()
    val member = memberList.find { it.id == memberId }

    if (member == null) {
        LaunchedEffect(Unit) { onNavigateBack() }
        return
    }

    var nama by remember { mutableStateOf(member.namaLengkap) }
    var nim by remember { mutableStateOf(member.nomorInduk) }
    var prodi by remember { mutableStateOf(member.programStudi) }
    var alamat by remember { mutableStateOf(member.alamat) }
    var telepon by remember { mutableStateOf(member.nomorTelepon) }
    var email by remember { mutableStateOf(member.email) }
    var jenisKeanggotaan by remember { mutableStateOf(member.jenisKeanggotaan) }

    var emailError by remember { mutableStateOf(false) }
    var nimError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    var expanded by remember { mutableStateOf(false) }
    val opsiKeanggotaan = listOf("Mahasiswa", "Dosen", "Staf", "Umum")

    val navyBlue = Color(0xFF0A2E65)
    val accentBlue = Color(0xFF1554D1)
    val bgLight = Color(0xFFF4F6FA)
    val borderGray = Color(0xFFD0D7DE)
    val errorRed = Color(0xFFD32F2F)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bgLight)
            .verticalScroll(rememberScrollState())
    ) {
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
                        text = "Edit Data Anggota",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Perbarui informasi data anggota perpustakaan",
                    color = Color.White.copy(alpha = 0.85f),
                    fontSize = 14.sp,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .offset(y = (-24).dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // 1. Nama Lengkap
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Nama Lengkap", fontWeight = FontWeight.SemiBold, fontSize = 13.sp, color = Color(0xFF334155), modifier = Modifier.padding(bottom = 6.dp))
                    OutlinedTextField(
                        value = nama,
                        onValueChange = { nama = it },
                        shape = RoundedCornerShape(10.dp),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = accentBlue, unfocusedBorderColor = borderGray),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                // 2. NIM / NIP
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(text = "NIM / NIP", fontWeight = FontWeight.SemiBold, fontSize = 13.sp, color = Color(0xFF334155), modifier = Modifier.padding(bottom = 6.dp))
                    OutlinedTextField(
                        value = nim,
                        onValueChange = {
                            nim = it
                            nimError = it.isNotEmpty() && it.length < 8
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        isError = nimError,
                        shape = RoundedCornerShape(10.dp),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = if (nimError) errorRed else accentBlue, unfocusedBorderColor = if (nimError) errorRed else borderGray),
                        modifier = Modifier.fillMaxWidth()
                    )
                    if (nimError) {
                        Text("NIM/NIP minimal harus 8 digit", color = errorRed, fontSize = 12.sp, modifier = Modifier.padding(top = 4.dp))
                    }
                }

                // 3. Program Studi
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Program Studi / Unit", fontWeight = FontWeight.SemiBold, fontSize = 13.sp, color = Color(0xFF334155), modifier = Modifier.padding(bottom = 6.dp))
                    OutlinedTextField(
                        value = prodi,
                        onValueChange = { prodi = it },
                        shape = RoundedCornerShape(10.dp),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = accentBlue, unfocusedBorderColor = borderGray),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                // 4. Alamat
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Alamat", fontWeight = FontWeight.SemiBold, fontSize = 13.sp, color = Color(0xFF334155), modifier = Modifier.padding(bottom = 6.dp))
                    OutlinedTextField(
                        value = alamat,
                        onValueChange = { alamat = it },
                        minLines = 3,
                        shape = RoundedCornerShape(10.dp),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = accentBlue, unfocusedBorderColor = borderGray),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                // 5. Nomor Telepon
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Nomor Telepon", fontWeight = FontWeight.SemiBold, fontSize = 13.sp, color = Color(0xFF334155), modifier = Modifier.padding(bottom = 6.dp))
                    OutlinedTextField(
                        value = telepon,
                        onValueChange = { telepon = it },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        shape = RoundedCornerShape(10.dp),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = accentBlue, unfocusedBorderColor = borderGray),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                // 6. Email
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Email", fontWeight = FontWeight.SemiBold, fontSize = 13.sp, color = Color(0xFF334155), modifier = Modifier.padding(bottom = 6.dp))
                    OutlinedTextField(
                        value = email,
                        onValueChange = {
                            email = it
                            emailError = it.isNotEmpty() && !Patterns.EMAIL_ADDRESS.matcher(it).matches()
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        isError = emailError,
                        shape = RoundedCornerShape(10.dp),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = if (emailError) errorRed else accentBlue, unfocusedBorderColor = if (emailError) errorRed else borderGray),
                        modifier = Modifier.fillMaxWidth()
                    )
                    if (emailError) {
                        Text("Format email tidak valid", color = errorRed, fontSize = 12.sp, modifier = Modifier.padding(top = 4.dp))
                    }
                }

                // 7. Jenis Keanggotaan Dropdown
                Column {
                    Text(
                        text = "Jenis Keanggotaan",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 13.sp,
                        color = Color(0xFF334155),
                        modifier = Modifier.padding(bottom = 6.dp)
                    )
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = it },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = jenisKeanggotaan,
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                            shape = RoundedCornerShape(10.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = accentBlue,
                                unfocusedBorderColor = borderGray,
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White
                            ),
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth()
                        )
                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            opsiKeanggotaan.forEach { opsi ->
                                DropdownMenuItem(
                                    text = { Text(opsi) },
                                    onClick = {
                                        jenisKeanggotaan = opsi
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                }

                if (errorMessage.isNotEmpty()) {
                    Text(text = errorMessage, color = errorRed, fontSize = 13.sp)
                }

                Button(
                    onClick = {
                        if (nama.isBlank() || nim.isBlank() || prodi.isBlank() || alamat.isBlank() || telepon.isBlank() || email.isBlank() || jenisKeanggotaan.isBlank()) {
                            errorMessage = "Semua kolom wajib diisi!"
                        } else if (nim.length < 8) {
                            nimError = true
                            errorMessage = "NIM/NIP minimal harus 8 digit!"
                        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                            emailError = true
                            errorMessage = "Format email tidak valid!"
                        } else {
                            errorMessage = ""
                            viewModel.updateMember(memberId, nama, nim, prodi, alamat, telepon, email, jenisKeanggotaan)
                            onNavigateBack()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .padding(top = 4.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = navyBlue)
                ) {
                    Text("Simpan Perubahan", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.White)
                }
            }
        }
    }
}