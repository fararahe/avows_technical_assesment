package com.avows.home.ui.home.adapter

import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter

class CategorySpinnerAdapter(
    context: Context,
    data: List<String>,
    private val selectedItem: (String) -> Unit,
) : ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, data),
AdapterView.OnItemSelectedListener{

    init {
        setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
        val selectedCategory = parent.getItemAtPosition(position) as String
        selectedItem.invoke(selectedCategory)
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        // do nothing
    }
}