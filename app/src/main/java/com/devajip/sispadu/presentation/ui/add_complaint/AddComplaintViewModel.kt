package com.devajip.sispadu.presentation.ui.add_complaint

import androidx.lifecycle.ViewModel

class AddComplaintViewModel : ViewModel() {

    val listStaff : MutableList<String> = mutableListOf()
        get() {
            if (field.isEmpty()) {
                field.add("Staff 1")
                field.add("Staff 2")
                field.add("Staff 3")
            }
            return field
        }
}