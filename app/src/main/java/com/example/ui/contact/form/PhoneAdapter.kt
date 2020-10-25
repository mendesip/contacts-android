package com.example.ui.contact.form

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.data.phone.Phone
import com.example.data.phone.PhoneType
import com.example.data.phone.PhoneTypeConverter
import com.example.ui.contact.databinding.ItemPhoneBinding
import com.example.utils.simpleOnItemSelectedCallback

class PhoneAdapter : ListAdapter<Phone, PhoneAdapter.PhoneViewHolder>(diffCallback) {

    var onRemovePhone: (Int) -> Unit = {}

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Phone>() {
            override fun areItemsTheSame(oldItem: Phone, newItem: Phone): Boolean {
                return oldItem.number == newItem.number
            }

            override fun areContentsTheSame(oldItem: Phone, newItem: Phone): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PhoneViewHolder {
        val binding = ItemPhoneBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PhoneViewHolder(binding, onRemovePhone)
    }

    override fun onBindViewHolder(
        holder: PhoneViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }

    class PhoneViewHolder(
        private val binding: ItemPhoneBinding,
        private val onRemovePhone: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private val phoneTypes: List<String> = PhoneType.values().map {
            itemView.context.getString(it.typeName)
        }

        private val typeAdapter = ArrayAdapter(
            itemView.context,
            android.R.layout.simple_spinner_dropdown_item,
            phoneTypes
        )

        fun bind(phone: Phone) {
            binding.phone = phone
            binding.removeBtn.setOnClickListener { onRemovePhone(adapterPosition) }
            binding.phoneType.adapter = typeAdapter
            binding.phoneType.setSelection(phone.type.ordinal)
            binding.phoneType.onItemSelectedListener = simpleOnItemSelectedCallback {
                phone.type = PhoneTypeConverter().intToPhoneType(it)
            }
        }
    }
}