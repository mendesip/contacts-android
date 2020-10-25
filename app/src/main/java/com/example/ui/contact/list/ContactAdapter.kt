package com.example.ui.contact.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.data.contact.Contact
import com.example.ui.contact.databinding.ItemContactBinding

class ContactAdapter :
    ListAdapter<ContactAdapter.ContactItem, ContactAdapter.ContactViewHolder>(diffCallback) {

    var onClickContact: (Contact) -> Unit = {}

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<ContactItem>() {
            override fun areItemsTheSame(oldItem: ContactItem, newItem: ContactItem): Boolean {
                return oldItem.contact.id == newItem.contact.id
            }

            override fun areContentsTheSame(oldItem: ContactItem, newItem: ContactItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContactViewHolder {
        val binding = ItemContactBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ContactViewHolder(binding, onClickContact)
    }

    override fun onBindViewHolder(
        holder: ContactViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }

    class ContactViewHolder(
        private val binding: ItemContactBinding,
        private val onClickContact: (Contact) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ContactItem) {
            binding.item = item
            binding.root.setOnClickListener { onClickContact(item.contact) }
        }
    }

    data class ContactItem(val contact: Contact, val firstStartsWith: Boolean)
}