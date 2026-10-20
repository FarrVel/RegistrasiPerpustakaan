package com.example.registrasiperpustakaan

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface MemberDao {
    // Mengambil semua data anggota secara langsung (real-time)
    @Query("SELECT * FROM members")
    fun getAllMembers(): Flow<List<Member>>

    // Mencari anggota spesifik berdasarkan ID
    @Query("SELECT * FROM members WHERE id = :memberId")
    suspend fun getMemberById(memberId: String): Member?

    // Menambah anggota baru
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMember(member: Member)

    // Menyimpan perubahan (Edit)
    @Update
    suspend fun updateMember(member: Member)

    // Menghapus anggota
    @Delete
    suspend fun deleteMember(member: Member)
}