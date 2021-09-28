package com.challenge.app.flow.main.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.challenge.app.R
import com.challenge.app.databinding.AdapterItemBinding
import com.challenge.app.models.Stock
import com.challenge.app.utils.setSafeOnClickListener
import java.util.*

class RVListAdapter(
    private val dataList: ArrayList<Stock>?,
    private val onItemClicked: (item: Stock) -> Unit = {}
) : RecyclerView.Adapter<RVListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            AdapterItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return dataList?.size ?: kotlin.run { 0 }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        dataList?.get(position)?.let { item ->
            holder.loadData(item)

            with(holder.binding) {
                this.root.setSafeOnClickListener { onItemClicked(item) }
            }
        }
    }

    class ViewHolder(
        val binding: AdapterItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun loadData(item: Stock?) = with(binding) {
            tvName.text = item?.name
            tvPrice.text = binding.root.resources.getString(R.string.price, item?.price ?: "-")
        }
    }

    fun setData(records: List<Stock>) {
        dataList?.clear()
        dataList?.addAll(records)
        notifyDataSetChanged()
    }
}
