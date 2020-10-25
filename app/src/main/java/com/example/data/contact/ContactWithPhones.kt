package com.example.data.contact

import androidx.room.Embedded
import androidx.room.Relation
import com.example.data.phone.Phone

data class ContactWithPhones(
    @Embedded val contact: Contact = Contact(),
    @Relation(
        parentColumn = "id",
        entityColumn = "contactId"
    )
    val phones: List<Phone> = listOf()
)