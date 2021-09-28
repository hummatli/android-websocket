package com.challenge.app.flow.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.challenge.app.base.BaseFragment
import com.challenge.app.databinding.FragmentMainPageBinding
import com.challenge.app.flow.main.adapter.RVListAdapter
import com.challenge.app.models.Stock
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainPageFragment : BaseFragment() {

    private val viewModel: MainPageViewModel by viewModel()
    private lateinit var binding: FragmentMainPageBinding

    private val rvListAdapter by lazy {
        RVListAdapter(
            ArrayList()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.listStateLiveData.observe(viewLifecycleOwner, { handleState(it) })
        viewModel.effect.observe(viewLifecycleOwner, { handleEffect(it) })
    }

    override fun setupViews(rootView: View) {
        binding.rvItems.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = rvListAdapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.start()
    }

    override fun onPause() {
        viewModel.stop()
        super.onPause()
    }

    private fun handleState(list: List<Stock>) {
        rvListAdapter.setData(list)
    }

    private fun handleEffect(effect: Effect) = when(effect) {
        is Effect.ShowErrorMessage -> {
            showError(effect.ex, binding.root)
        }
    }
}
