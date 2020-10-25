package com.example.data.contact

import android.database.sqlite.SQLiteConstraintException
import androidx.lifecycle.LiveData
import com.example.data.phone.Phone
import com.example.data.phone.PhoneDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ContactRepository(
    private val contactDao: ContactDao,
    private val phoneDao: PhoneDao
) {
    fun findContacts(): LiveData<List<Contact>> {
        return contactDao.findAll()
    }

    suspend fun findContact(contactId: Long): ContactWithPhones? {
        return withContext(Dispatchers.IO) {
            contactDao.find(contactId)
        }
    }

    suspend fun saveContact(contact: Contact, phoneList: List<Phone>): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                handleSaveContact(contact, phoneList)
                true
            } catch (e: SQLiteConstraintException){
                false
            }
        }
    }

    private suspend fun handleSaveContact(
        contact: Contact,
        phoneList: List<Phone>
    ) {
        if (contact.id == 0L) {
            contactDao.save(contact, phoneList)
        } else {
            contactDao.update(contact, phoneList)
        }
    }

    suspend fun deleteContact(contactId: Long) {
        withContext(Dispatchers.IO) {
            phoneDao.clearByContact(contactId)
            contactDao.delete(contactId)
        }
    }
}