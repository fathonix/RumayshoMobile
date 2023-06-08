package com.idn.rumayshomobile.utils.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.idn.rumayshomobile.databinding.GroupRowItemBinding
import com.idn.rumayshomobile.models.collection.Collection

class CollectionListAdapter : PagingDataAdapter<Collection, CollectionListAdapter.GroupViewHolder>(POST_COMPARATOR) {

    inner class GroupViewHolder(val binding: GroupRowItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object {
        private val POST_COMPARATOR = object : DiffUtil.ItemCallback<Collection>() {
            override fun areItemsTheSame(oldItem: Collection, newItem: Collection): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Collection, newItem: Collection): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        val currentItem = getItem(position)

        if (currentItem != null && currentItem.count > 0) {
            holder.binding.apply {
                tvTitle.text = currentItem.name
                tvQuantity.text = currentItem.count.toString()

                root.setOnClickListener {
                    onItemClickCallback?.onItemClicked(currentItem.id)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = GroupViewHolder(
        GroupRowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(groupId: Int)
    }

}