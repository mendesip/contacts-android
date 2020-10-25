package com.example.ui.contact.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ui.contact.R
import com.example.utils.simpleQueryChangeListener
import kotlinx.android.synthetic.main.fragment_contact_list.*
import org.koin.android.viewmodel.ext.android.viewModel


class ContactListFragment : Fragment() {

    private val contactsViewModel: ContactListViewModel by viewModel()
    private val contactAdapter = ContactAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_contact_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureContactList()
        configureFabButton()
        configureSearchBar()
        contactsViewModel.filteredContacts().observe(viewLifecycleOwner) {
            handleContactList(it)
        }
    }

    private fun configureSearchBar() {
        val searchView = toolbar.menu.findItem(R.id.action_search).actionView as SearchView?
        searchView?.setOnQueryTextListener(simpleQueryChangeListener {
            contactsViewModel.setFilter(it)
        })
    }

    private fun configureFabButton() {
        fab.setOnClickListener {
            findNavController().navigate(R.id.action_ContactList_to_ContactForm)
        }
    }

    private fun configureContactList() {
        contact_list.layoutManager = LinearLayoutManager(requireContext())
        contact_list.adapter = contactAdapter
        contactAdapter.onClickContact = {
            val action = ContactListFragmentDirections.actionContactListToContactForm(it.id)
            findNavController().navigate(action)
        }
    }

    private fun handleContactList(it: List<ContactAdapter.ContactItem>) {
        contactAdapter.submitList(it)
        if (it.isEmpty()) {
            empty.visibility = View.VISIBLE
        } else {
            empty.visibility = View.GONE
        }
    }
}