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
fun RegistrationScreen(
    viewModel: MemberViewModel,
    onNavigateToList: () -> Unit,
    onNavigateBack: () -> Unit
) {
    var nama by remember { mutableStateOf("") }
    var nim by remember { mutableStateOf("") }
    var prodi by remember { mutableStateOf("") }
    var alamat by remember { mutableStateOf("") }
    var telepon by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var jenisKeanggotaan by remember { mutableStateOf("Mahasiswa") }

    var emailError by remember { mutableStateOf(false) }
    var nimError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    var expanded by remember { mutableStateOf(false) }
    val opsiKeanggotaan = listOf("Mahasiswa", "Dosen", "Staf", "Umum")

    val navyBlue = Color(0xFF0A2E65)
    val accentBlue = Color(0xFF1554D1)
    val bgLight = Color(0xFFF4F6FA)
    val borderGray = Color(0xFFD0D7DE)

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
                        text = "Formulir Pendaftaran",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Lengkapi data diri calon anggota",
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
                FormInputField(
                    label = "Nama Lengkap",
                    value = nama,
                    onValueChange = { nama = it },
                    placeholder = "Masukkan nama lengkap"
                )

                Column {
                    FormInputField(
                        label = "NIM / NIP",
                        value = nim,
                        onValueChange = {
                            nim = it
                            nimError = it.isNotEmpty() && it.length < 8
                        },
                        placeholder = "Contoh: 231001001",
                        keyboardType = KeyboardType.Number,
                        isError = nimError
                    )
                    if (nimError) {
                        Text(
                            text = "NIM/NIP minimal harus 8 digit",
                            color = Color(0xFFD32F2F),
                            fontSize = 12.sp,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }

                FormInputField(
                    label = "Program Studi / Unit",
                    value = prodi,
                    onValueChange = { prodi = it },
                    placeholder = "Contoh: Teknik Informatika"
                )

                FormInputField(
                    label = "Alamat",
                    value = alamat,
                    onValueChange = { alamat = it },
                    placeholder = "Masukkan alamat lengkap",
                    minLines = 3
                )

                FormInputField(
                    label = "Nomor Telepon",
                    value = telepon,
                    onValueChange = { telepon = it },
                    placeholder = "Contoh: 081234567890",
                    keyboardType = KeyboardType.Phone
                )

                Column {
                    FormInputField(
                        label = "Email",
                        value = email,
                        onValueChange = {
                            email = it
                            emailError = it.isNotEmpty() && !Patterns.EMAIL_ADDRESS.matcher(it).matches()
                        },
                        placeholder = "nama@kampus.ac.id",
                        keyboardType = KeyboardType.Email,
                        isError = emailError
                    )
                    if (emailError) {
                        Text(
                            text = "Format email tidak valid",
                            color = Color(0xFFD32F2F),
                            fontSize = 12.sp,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }

                // Dropdown Murni Saja (Tanpa Tombol Chip di atasnya)
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
                    Text(
                        text = errorMessage,
                        color = Color(0xFFD32F2F),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )
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
                            viewModel.addMember(nama, nim, prodi, alamat, telepon, email, jenisKeanggotaan)
                            onNavigateToList()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .padding(top = 4.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = navyBlue)
                ) {
                    Text("Daftar", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.White)
                }
            }
        }
    }
}

@Composable
fun FormInputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "",
    keyboardType: KeyboardType = KeyboardType.Text,
    minLines: Int = 1,
    isError: Boolean = false
) {
    val accentBlue = Color(0xFF1554D1)
    val borderGray = Color(0xFFD0D7DE)
    val errorRed = Color(0xFFD32F2F)

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            fontWeight = FontWeight.SemiBold,
            fontSize = 13.sp,
            color = Color(0xFF334155),
            modifier = Modifier.padding(bottom = 6.dp)
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder, color = Color.LightGray, fontSize = 14.sp) },
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            minLines = minLines,
            maxLines = if (minLines > 1) 5 else 1,
            isError = isError,
            shape = RoundedCornerShape(10.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = if (isError) errorRed else accentBlue,
                unfocusedBorderColor = if (isError) errorRed else borderGray,
                errorBorderColor = errorRed,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}