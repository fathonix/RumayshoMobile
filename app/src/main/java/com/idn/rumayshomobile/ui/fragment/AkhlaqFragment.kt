package com.idn.rumayshomobile.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.idn.rumayshomobile.databinding.FragmentViewPagerBinding
import com.idn.rumayshomobile.models.post.Posts
import com.idn.rumayshomobile.ui.view.DetailActivity
import com.idn.rumayshomobile.utils.adapter.PostListAdapter
import com.idn.rumayshomobile.utils.viewmodel.SharedViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class AkhlaqFragment : Fragment() {
    private lateinit var binding: FragmentViewPagerBinding
    private lateinit var viewModel: SharedViewModel
    private lateinit var postAdapter: PostListAdapter
    private lateinit var posts: LiveData<PagingData<Posts>>

    companion object {
        const val CATEGORY_ID = 4
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentViewPagerBinding.inflate(layoutInflater, container, false)
        viewModel = ViewModelProvider(this)[SharedViewModel::class.java]
        postAdapter = PostListAdapter()
        posts = viewModel.getPostByCategory(CATEGORY_ID)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvPosts.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = postAdapter
            postAdapter.setOnItemClickCallback(
                object : PostListAdapter.OnItemClickCallback {
                    override fun onItemClicked(post: Posts) {
                        startActivity(
                            Intent(context, DetailActivity::class.java)
                                .putExtra("data", post)
                        )
                    }
                }
            )
        }

        lifecycleScope.launch {
            postAdapter.loadStateFlow.map { it.refresh }
                .distinctUntilChanged()
                .collect { loadState ->
                    when (loadState) {
                        is LoadState.Loading -> {
                            binding.rvPosts.visibility = View.INVISIBLE
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is LoadState.NotLoading -> {
                            binding.progressBar.visibility = View.INVISIBLE
                            binding.rvPosts.visibility = View.VISIBLE
                        }
                        is LoadState.Error -> {
                            binding.progressBar.visibility = View.INVISIBLE
                            binding.rvPosts.visibility = View.INVISIBLE
                        }
                    }
                }
        }

        posts.observe(viewLifecycleOwner) { pagingData ->
            postAdapter.submitData(lifecycle, pagingData)
        }
    }
}