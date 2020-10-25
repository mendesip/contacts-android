package com.example.koin

import com.example.data.ContactDatabase
import com.example.data.contact.ContactRepository
import com.example.ui.contact.form.ContactFormViewModel
import com.example.ui.contact.list.ContactListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { ContactDatabase.getDatabase(androidContext()) }

    factory { get(ContactDatabase::class).contactDao() }
    factory { get(ContactDatabase::class).phoneDao() }
    factory { ContactRepository(get(), get()) }

    viewModel { ContactListViewModel(get()) }
    viewModel { ContactFormViewModel(get()) }
}