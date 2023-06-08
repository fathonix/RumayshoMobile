package com.idn.rumayshomobile.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.switchMap
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.idn.rumayshomobile.databinding.ActivitySearchBinding
import com.idn.rumayshomobile.models.post.Posts
import com.idn.rumayshomobile.utils.adapter.PostListAdapter
import com.idn.rumayshomobile.utils.viewmodel.SharedViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var viewModel: SharedViewModel
    private lateinit var postAdapter: PostListAdapter
    private lateinit var posts: LiveData<PagingData<Posts>>
    private val keyword = MutableLiveData<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[SharedViewModel::class.java]
        postAdapter = PostListAdapter()
        posts = keyword.switchMap {
            if (!it.isNullOrBlank()) viewModel.searchPost(it) else MutableLiveData()
        }
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.searchView.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean = true

                override fun onQueryTextChange(newText: String?): Boolean {
                    keyword.value = newText
                    return true
                }
            }
        )

        binding.rvPosts.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = postAdapter
            postAdapter.setOnItemClickCallback(
                object : PostListAdapter.OnItemClickCallback {
                    override fun onItemClicked(post: Posts) {
                        startActivity(
                            Intent(context, DetailActivity::class.java)
                                .putExtra(DetailActivity.EXTRA_DATA, post)
                        )
                    }
                }
            )
        }

        // binding.rvPosts.clear

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

        posts.observe(this) { pagingData ->
            postAdapter.submitData(lifecycle, pagingData)
        }
    }
}