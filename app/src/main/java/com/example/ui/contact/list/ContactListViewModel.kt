package com.example.ui.contact.list

import androidx.lifecycle.*
import com.example.data.contact.Contact
import com.example.data.contact.ContactRepository
import java.util.*

class ContactListViewModel(
    private val contactRepository: ContactRepository
) : ViewModel() {

    private val filter = MutableLiveData("")

    fun setFilter(filter: String) {
        this.filter.value = filter
    }

    fun filteredContacts(): LiveData<List<ContactAdapter.ContactItem>> =
        contactRepository.findContacts().switchMap { items ->
            filter.map { query ->
                handleFilter(items, query).toContactItems()
            }
        }

    private fun handleFilter(contacts: List<Contact>, query: String): List<Contact> {
        return if (query != "") {
            contacts.filterContacts(query)
        } else {
            contacts
        }
    }

    private fun List<Contact>.filterContacts(query: String) = filter {
        it.name.toLowerCase(Locale.getDefault())
            .contains(query.toLowerCase(Locale.getDefault()))
    }

    companion object {
        fun List<Contact>.toContactItems() = map { contact ->
            ContactAdapter.ContactItem(contact, contact == firstStartsWith(contact))
        }

        private fun List<Contact>.firstStartsWith(contact: Contact) =
            first { it.name[0] == contact.name[0] }
    }
}