package com.example.registrasiperpustakaan

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "members")
data class Member(
    @PrimaryKey val id: String, // Menjadikan ID sebagai Primary Key
    val namaLengkap: String,
    val nomorInduk: String,
    val programStudi: String,
    val alamat: String,
    val nomorTelepon: String,
    val email: String,
    val jenisKeanggotaan: String
)