package com.challenge.app.flow

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.challenge.app.R
import com.challenge.app.base.BaseActivity


class MainActivity : BaseActivity(R.layout.activity_main) {

    private val navController: NavController by lazy {
        findNavController(R.id.main_nav_fragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navController.setGraph(R.navigation.main_nav_graph)
    }
}