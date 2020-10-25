package com.example.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.data.contact.Contact
import com.example.data.contact.ContactDao
import com.example.data.phone.Phone
import com.example.data.phone.PhoneDao
import com.example.data.phone.PhoneTypeConverter

@Database(entities = [Contact::class, Phone::class], version = 1)
@TypeConverters(PhoneTypeConverter::class)
abstract class ContactDatabase : RoomDatabase() {
    abstract fun contactDao(): ContactDao
    abstract fun phoneDao(): PhoneDao

    companion object {
        @Volatile
        private var INSTANCE: ContactDatabase? = null

        fun getDatabase(context: Context): ContactDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ContactDatabase::class.java,
                    "contacts.db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}