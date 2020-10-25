package com.example.ui.contact.form

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.data.phone.PhoneType
import com.example.ui.contact.R
import com.example.ui.contact.databinding.FragmentContactFormBinding
import com.example.utils.hideKeyboard
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_contact_form.*
import org.koin.android.viewmodel.ext.android.viewModel


class ContactFormFragment : Fragment() {

    private val contactFormViewModel: ContactFormViewModel by viewModel()

    private val args: ContactFormFragmentArgs by navArgs()

    private lateinit var binding: FragmentContactFormBinding

    private val phoneAdapter = PhoneAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContactFormBinding.inflate(
            inflater,
            container,
            false
        )
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureBinding()
        configureToolbar()
        configurePhoneList()
        configureButtons()
        contactFormViewModel.phones.observe(viewLifecycleOwner) {
            phoneAdapter.submitList(it.toList())
        }
    }

    private fun configureBinding() {
        contactFormViewModel.init(args.contactId)
        binding.contactId = args.contactId
        binding.viewModel = contactFormViewModel
    }

    private fun configureToolbar() {
        toolbar.setNavigationOnClickListener {
            requireActivity().hideKeyboard()
            findNavController().popBackStack()
        }
        toolbar.setOnMenuItemClickListener {
            handleToolbarActions(it)
        }
    }

    private fun handleToolbarActions(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_save) {
            saveContact()
            return true
        }
        return false
    }

    private fun configurePhoneList() {
        phone_list.layoutManager = LinearLayoutManager(requireContext())
        phone_list.adapter = phoneAdapter
        phone_list.setHasFixedSize(false)
        phoneAdapter.onRemovePhone = {
            contactFormViewModel.removePhone(it)
        }
    }

    private fun configureButtons() {
        binding.addPhone.setOnClickListener {
            contactFormViewModel.addPhone(PhoneType.MOBILE, "")
        }

        binding.deleteContact.setOnClickListener {
            lifecycleScope.launchWhenResumed {
                contactFormViewModel.deleteContact()
                showToastMessage(R.string.contact_deleted)
                findNavController().popBackStack()
            }
        }
    }

    private fun saveContact() {
        lifecycleScope.launchWhenResumed {
            if (contactFormViewModel.name.isNotBlank()) {
                tryToSaveContact()
            } else {
                showSnackBar(R.string.empty_contact_message)
            }
        }
    }

    private suspend fun tryToSaveContact() {
        if (contactFormViewModel.saveContact()) {
            requireActivity().hideKeyboard()
            findNavController().popBackStack()
            showToastMessage(R.string.contact_saved)
        } else {
            showSnackBar(R.string.existing_phone_message)
        }
    }

    private fun showSnackBar(@StringRes message: Int) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    private fun showToastMessage(@StringRes message: Int) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }
}