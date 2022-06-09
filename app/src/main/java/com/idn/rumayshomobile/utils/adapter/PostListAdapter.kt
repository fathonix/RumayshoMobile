package com.idn.rumayshomobile.utils.adapter

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.idn.rumayshomobile.models.post.Posts
import com.idn.rumayshomobile.databinding.PostRowItemBinding

class PostListAdapter : PagingDataAdapter<Posts, PostListAdapter.PostViewHolder>(POST_COMPARATOR) {

    inner class PostViewHolder(val binding: PostRowItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object {
        private val POST_COMPARATOR = object : DiffUtil.ItemCallback<Posts>() {
            override fun areItemsTheSame(oldItem: Posts, newItem: Posts): Boolean {

                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Posts, newItem: Posts): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val currentItem = getItem(position)

        if (currentItem != null) {
            holder.binding.apply {
                if (!currentItem.embedded?.wpFeaturedMedia.isNullOrEmpty()) {
                    Glide.with(root.context)
                        .load(currentItem.embedded?.wpFeaturedMedia?.get(0)?.sourceUrl)
                        .placeholder(ColorDrawable(Color.parseColor("#e2e2e2")))
                        .into(imgBanner)
                }

                @Suppress("DEPRECATION")
                tvNameImageBanner.text = Html.fromHtml(currentItem.title?.rendered)

                root.setOnClickListener {
                    onItemClickCallback?.onItemClicked(currentItem)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PostViewHolder(
        PostRowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(post: Posts)
    }

}