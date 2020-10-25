package com.example.utils

import android.view.View
import android.widget.AdapterView
import androidx.appcompat.widget.SearchView

fun simpleOnItemSelectedCallback(
    onSelectItem: (Int) -> Unit
) = object : AdapterView.OnItemSelectedListener {
    override fun onItemSelected(
        parent: AdapterView<*>?,
        view: View?,
        position: Int,
        id: Long
    ) {
        onSelectItem(position)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        onSelectItem(0)
    }
}

fun simpleQueryChangeListener(
    onQueryChange: (String) -> Unit
) = object : SearchView.OnQueryTextListener {
    override fun onQueryTextSubmit(query: String?): Boolean {
        onQueryChange(query ?: "")
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        onQueryChange(newText ?: "")
        return true
    }
}