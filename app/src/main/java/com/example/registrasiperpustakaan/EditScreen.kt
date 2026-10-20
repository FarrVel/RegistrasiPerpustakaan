package com.example.registrasiperpustakaan

import android.util.Patterns
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditScreen(
    memberId: String,
    viewModel: MemberViewModel,
    onNavigateBack: () -> Unit
) {
    val member = viewModel.getMemberById(memberId)

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

    var errorMessage by remember { mutableStateOf("") }

    // --- Persiapan Dropdown ---
    var expanded by remember { mutableStateOf(false) }
    val opsiKeanggotaan = listOf("Mahasiswa", "Dosen", "Unit Kerja", "Umum")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detail & Edit Anggota") },
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

            // Menggunakan Keyboard Angka untuk NIM
            OutlinedTextField(
                value = nim,
                onValueChange = { nim = it },
                label = { Text("NIM / NIP") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            OutlinedTextField(value = prodi, onValueChange = { prodi = it }, label = { Text("Program Studi / Unit") }, modifier = Modifier.fillMaxWidth())

            // Alamat dibuat multi-baris (lebih lebar)
            OutlinedTextField(
                value = alamat,
                onValueChange = { alamat = it },
                label = { Text("Alamat") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                maxLines = 5
            )

            // Menggunakan Keyboard Angka/Telepon
            OutlinedTextField(
                value = telepon,
                onValueChange = { telepon = it },
                label = { Text("Nomor Telepon") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
            )

            OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") }, modifier = Modifier.fillMaxWidth())

            // --- Dropdown Jenis Keanggotaan ---
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
                    } else if (nim.length < 8) { // Validasi NIM minimal 8 digit
                        errorMessage = "NIM/NIP minimal harus 8 digit!"
                    } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        errorMessage = "Format email tidak valid!"
                    } else {
                        errorMessage = ""
                        viewModel.updateMember(memberId, nama, nim, prodi, alamat, telepon, email, jenisKeanggotaan)
                        onNavigateBack()
                    }
                },
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
            ) {
                Text("Simpan Perubahan")
            }
        }
    }
}