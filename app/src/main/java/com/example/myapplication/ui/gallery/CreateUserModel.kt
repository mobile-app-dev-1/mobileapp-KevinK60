package com.example.myapplication.ui.gallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CreateUserModel : ViewModel(){
    private val _text = MutableLiveData<String>().apply {
        value = "This is user Fragment please help me"

    }
    val text: LiveData<String> = _text
}
