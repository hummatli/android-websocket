package com.challenge.app.extensions

import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<T>.forceRefresh() {
    this.value = value
}