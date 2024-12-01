package com.example.kocom.view.home.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.kocom.databinding.UserItemRowBinding
import com.example.kocom.models.Item


class UserAdapter(private val onItemClick: ((Item) -> Unit)) :
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    private val differ = AsyncListDiffer(this, differCallback)

    fun submitList(list: List<Item>) {
        differ.submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = UserItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = differ.currentList[position]
        holder.bind(model)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class ViewHolder(private var binding: UserItemRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Item) {
            binding.apply {
                tvName.text = item.title
                tvDesc.text = "${item.description}"
                tvDate.text = "Create at : ${item.date}"
                tvDate.paintFlags =
                    tvDate.paintFlags or Paint.UNDERLINE_TEXT_FLAG
                root.setOnClickListener {
                    onItemClick.invoke(item)
                }
            }
        }
    }

    companion object {
        val differCallback = object : DiffUtil.ItemCallback<Item>() {
            override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
                return oldItem.index == newItem.index
            }

            override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
                return oldItem == newItem
            }

        }
    }
}