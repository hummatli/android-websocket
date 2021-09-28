package com.challenge.app.base

import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity(layout: Int) : AppCompatActivity(layout) {

    override fun onStart() {
        super.onStart()
        setupViews()
    }

    protected fun setupViews() {}
}
