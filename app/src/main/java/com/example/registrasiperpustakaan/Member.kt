package com.example.registrasiperpustakaan

data class Member(
    val id: String,
    val namaLengkap: String,
    val nomorInduk: String,
    val programStudi: String,
    val alamat: String,
    val nomorTelepon: String,
    val email: String,
    val jenisKeanggotaan: String
)