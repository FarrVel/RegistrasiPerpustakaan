package com.example.registrasiperpustakaan

import android.util.Patterns
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(
    viewModel: MemberViewModel,
    onNavigateToList: () -> Unit,
    onNavigateBack: () -> Unit // <-- Tambahan rute untuk tombol kembali
) {
    var nama by remember { mutableStateOf("") }
    var nim by remember { mutableStateOf("") }
    var prodi by remember { mutableStateOf("") }
    var alamat by remember { mutableStateOf("") }
    var telepon by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var jenisKeanggotaan by remember { mutableStateOf("") }

    var errorMessage by remember { mutableStateOf("") }

    var expanded by remember { mutableStateOf(false) }
    val opsiKeanggotaan = listOf("Mahasiswa", "Dosen", "Unit Kerja", "Umum")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Formulir Pendaftaran") },
                // --- Tambahan Tombol Kembali ---
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Text("←", style = MaterialTheme.typography.headlineMedium, color = MaterialTheme.colorScheme.primary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(value = nama, onValueChange = { nama = it }, label = { Text("Nama Lengkap") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(
                value = nim,
                onValueChange = { nim = it },
                label = { Text("NIM / NIP") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number) // Memunculkan keyboard angka
            )
            OutlinedTextField(value = prodi, onValueChange = { prodi = it }, label = { Text("Program Studi / Unit") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(
                value = alamat,
                onValueChange = { alamat = it },
                label = { Text("Alamat") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3, // Membuat kotak alamat lebih besar (multi-baris)
                maxLines = 5
            )
            OutlinedTextField(
                value = telepon,
                onValueChange = { telepon = it },
                label = { Text("Nomor Telepon") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone) // Memunculkan keyboard telepon
            )
            OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") }, modifier = Modifier.fillMaxWidth())

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = it },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = jenisKeanggotaan,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Jenis Keanggotaan") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                    modifier = Modifier.menuAnchor().fillMaxWidth()
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

            if (errorMessage.isNotEmpty()) {
                Text(text = errorMessage, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodyMedium)
            }

            Button(
                onClick = {
                    if (nama.isBlank() || nim.isBlank() || prodi.isBlank() || alamat.isBlank() || telepon.isBlank() || email.isBlank() || jenisKeanggotaan.isBlank()) {
                        errorMessage = "Semua kolom wajib diisi!"
                    } else if (nim.length < 8) { // Validasi panjang minimal Nomor Induk
                        errorMessage = "NIM/NIP minimal harus 8 digit!"
                    } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        errorMessage = "Format email tidak valid (misal: nama@gmail.com)!"
                    } else {
                        errorMessage = ""
                        viewModel.addMember(nama, nim, prodi, alamat, telepon, email, jenisKeanggotaan)
                        onNavigateToList()
                    }
                },
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
            ) {
                Text("Daftar")
            }
        }
    }
}