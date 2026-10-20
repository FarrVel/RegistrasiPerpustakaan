package com.example.registrasiperpustakaan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID

class MemberViewModel(private val dao: MemberDao) : ViewModel() {

    // Mengambil daftar anggota secara langsung dari database secara real-time
    val memberList: StateFlow<List<Member>> = dao.getAllMembers()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addMember(
        namaLengkap: String, nomorInduk: String, programStudi: String,
        alamat: String, nomorTelepon: String, email: String, jenisKeanggotaan: String
    ) {
        val newMember = Member(
            id = UUID.randomUUID().toString(),
            namaLengkap = namaLengkap, nomorInduk = nomorInduk, programStudi = programStudi,
            alamat = alamat, nomorTelepon = nomorTelepon, email = email, jenisKeanggotaan = jenisKeanggotaan
        )
        // Menyimpan ke database di latar belakang
        viewModelScope.launch { dao.insertMember(newMember) }
    }

    fun updateMember(
        id: String, namaLengkap: String, nomorInduk: String, programStudi: String,
        alamat: String, nomorTelepon: String, email: String, jenisKeanggotaan: String
    ) {
        val updatedMember = Member(
            id = id,
            namaLengkap = namaLengkap, nomorInduk = nomorInduk, programStudi = programStudi,
            alamat = alamat, nomorTelepon = nomorTelepon, email = email, jenisKeanggotaan = jenisKeanggotaan
        )
        // Memperbarui database di latar belakang
        viewModelScope.launch { dao.updateMember(updatedMember) }
    }

    fun deleteMember(member: Member) {
        // Menghapus dari database di latar belakang
        viewModelScope.launch { dao.deleteMember(member) }
    }

    // Mencari anggota spesifik dari daftar yang sedang aktif
    fun getMemberById(id: String): Member? {
        return memberList.value.find { it.id == id }
    }
}

// Factory untuk membuat ViewModel yang butuh parameter DAO
class MemberViewModelFactory(private val dao: MemberDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MemberViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MemberViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}