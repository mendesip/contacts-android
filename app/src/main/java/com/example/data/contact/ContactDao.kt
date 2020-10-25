package com.example.data.contact

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.data.ContactDatabase
import com.example.data.phone.Phone

@Dao
abstract class ContactDao(
    private val database: ContactDatabase
) {
    @Query("SELECT * FROM contact ORDER BY contact.name")
    abstract fun findAll(): LiveData<List<Contact>>

    @Transaction
    @Query("SELECT * FROM contact WHERE contact.id = :id")
    abstract suspend fun find(id: Long): ContactWithPhones?

    @Query("DELETE FROM contact WHERE contact.id = :id")
    abstract suspend fun delete(id: Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun save(contact: Contact): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun update(contact: Contact)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @Transaction
    suspend fun save(contact: Contact, phones: List<Phone>) {
        val contactId = save(contact)
        phones.forEach { it.contactId = contactId }
        database.phoneDao().save(*phones.toTypedArray())
    }

    @Update(onConflict = OnConflictStrategy.REPLACE)
    @Transaction
    suspend fun update(contact: Contact, phones: List<Phone>) {
        update(contact)
        database.phoneDao().clearByContact(contact.id)
        database.phoneDao().save(*phones.toTypedArray())
    }
}