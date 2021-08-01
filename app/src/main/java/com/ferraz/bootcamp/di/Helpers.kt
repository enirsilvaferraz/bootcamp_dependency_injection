package com.ferraz.bootcamp.di

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity

object Helpers {

    class OnSelectListener(val onSelect: (String) -> Unit) : AdapterView.OnItemSelectedListener {

        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
            onSelect("${position + 1}")
        }

        override fun onNothingSelected(p0: AdapterView<*>?) {}
    }

    fun AppCompatActivity.getArrayAdapter() = ArrayAdapter<Any?>(
        this,
        android.R.layout.simple_spinner_item,
        resources.getStringArray(R.array.names)
    ).apply {
        setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    }
}