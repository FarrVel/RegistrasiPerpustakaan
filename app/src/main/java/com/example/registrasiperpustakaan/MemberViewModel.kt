package com.example.registrasiperpustakaan

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import java.util.UUID

class MemberViewModel : ViewModel() {
    private val _memberList = mutableStateListOf<Member>()
    val memberList: List<Member> get() = _memberList

    fun addMember(
        namaLengkap: String, nomorInduk: String, programStudi: String,
        alamat: String, nomorTelepon: String, email: String, jenisKeanggotaan: String
    ) {
        val newMember = Member(
            id = UUID.randomUUID().toString(),
            namaLengkap = namaLengkap, nomorInduk = nomorInduk, programStudi = programStudi,
            alamat = alamat, nomorTelepon = nomorTelepon, email = email, jenisKeanggotaan = jenisKeanggotaan
        )
        _memberList.add(newMember)
    }

    fun deleteMember(member: Member) {
        _memberList.remove(member)
    }

    // FITUR EDIT & DETAIL

    // Mengambil data spesifik berdasarkan ID
    fun getMemberById(id: String): Member? {
        return _memberList.find { it.id == id }
    }

    // Menyimpan perubahan data yang sudah diedit
    fun updateMember(
        id: String, namaLengkap: String, nomorInduk: String, programStudi: String,
        alamat: String, nomorTelepon: String, email: String, jenisKeanggotaan: String
    ) {
        val index = _memberList.indexOfFirst { it.id == id }
        if (index != -1) {
            _memberList[index] = Member(
                id = id, // ID tetap sama
                namaLengkap = namaLengkap, nomorInduk = nomorInduk, programStudi = programStudi,
                alamat = alamat, nomorTelepon = nomorTelepon, email = email, jenisKeanggotaan = jenisKeanggotaan
            )
        }
    }
}