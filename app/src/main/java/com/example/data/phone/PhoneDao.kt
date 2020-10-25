package com.example.data.phone

import androidx.room.*

@Dao
interface PhoneDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun save(vararg phone: Phone)

    @Query("DELETE FROM phone WHERE phone.contactId = :contactId")
    suspend fun clearByContact(contactId: Long)
}