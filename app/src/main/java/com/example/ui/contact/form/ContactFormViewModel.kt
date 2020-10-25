package com.example.ui.contact.form

import androidx.databinding.Bindable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.data.contact.Contact
import com.example.data.contact.ContactRepository
import com.example.data.contact.ContactWithPhones
import com.example.data.phone.Phone
import com.example.data.phone.PhoneType
import com.example.ui.common.ObservableViewModel
import com.example.ui.contact.BR
import kotlinx.coroutines.launch

class ContactFormViewModel(
    private val contactRepository: ContactRepository
) : ObservableViewModel() {

    companion object {
        fun List<Phone>.hasEmptyPhone() = any { it.number.isBlank() }
        fun List<Phone>.removeEmptyPhones() = filter { it.number.isNotBlank() }
    }

    private var contactId: Long = 0

    @get:Bindable
    var name: String = ""
        set(value) {
            if (field != value) {
                field = value
                notifyPropertyChanged(BR.name)
            }
        }

    @get:Bindable
    var email: String = ""
        set(value) {
            if (field != value) {
                field = value
                notifyPropertyChanged(BR.email)
            }
        }

    private val phoneList = mutableListOf<Phone>()
    val phones = MutableLiveData<List<Phone>>(phoneList)

    fun addPhone(type: PhoneType, number: String): Boolean {
        val canAddPhone = !phoneList.hasEmptyPhone()
        if (canAddPhone) {
            phoneList.add(Phone(contactId = contactId, number = number, type = type))
            phones.value = phoneList
        }
        return canAddPhone
    }

    fun removePhone(position: Int) {
        phoneList.removeAt(position)
        phones.value = phoneList
    }

    suspend fun saveContact(): Boolean {
        val contact = Contact(contactId, name, email)
        return contactRepository.saveContact(contact, phoneList.removeEmptyPhones())
    }

    suspend fun deleteContact() {
        contactRepository.deleteContact(contactId)
    }

    fun init(contactId: Long) {
        this.contactId = contactId
        addPhone(PhoneType.MOBILE, "")
        viewModelScope.launch {
            val contact = contactRepository.findContact(contactId)
            if (contact != null) {
                initEditContact(contact)
            }
        }
    }

    private fun initEditContact(contact: ContactWithPhones) {
        name = contact.contact.name
        email = contact.contact.email
        phoneList.clear()
        phoneList.addAll(contact.phones)
        phones.value = phoneList
    }
}