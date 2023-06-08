package com.idn.rumayshomobile.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.idn.rumayshomobile.NavMainDirections
import com.idn.rumayshomobile.databinding.FragmentCollectionBinding
import com.idn.rumayshomobile.models.collection.Collection
import com.idn.rumayshomobile.models.type.CollectionType
import com.idn.rumayshomobile.utils.adapter.CollectionListAdapter
import com.idn.rumayshomobile.utils.viewmodel.SharedViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class TagsFragment : Fragment() {

    private lateinit var binding: FragmentCollectionBinding
    private lateinit var viewModel: SharedViewModel
    private lateinit var tagAdapter: CollectionListAdapter
    private lateinit var tags: LiveData<PagingData<Collection>>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentCollectionBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[SharedViewModel::class.java]
        tagAdapter = CollectionListAdapter()
        tags = viewModel.getTags()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvCategories.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = tagAdapter
            tagAdapter.setOnItemClickCallback(
                object : CollectionListAdapter.OnItemClickCallback {
                    override fun onItemClicked(groupId: Int) {
                        findNavController().navigate(
                            NavMainDirections.actionToDetail(groupId, CollectionType.TAG)
                        )
                    }
                }
            )
        }

        lifecycleScope.launch {
            tagAdapter.loadStateFlow.map { it.refresh }
                .distinctUntilChanged()
                .collect { loadState ->
                    when (loadState) {
                        is LoadState.Loading -> {
                            binding.rvCategories.visibility = View.INVISIBLE
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is LoadState.NotLoading -> {
                            binding.progressBar.visibility = View.INVISIBLE
                            binding.rvCategories.visibility = View.VISIBLE
                        }
                        is LoadState.Error -> {
                            binding.progressBar.visibility = View.INVISIBLE
                            binding.rvCategories.visibility = View.INVISIBLE
                        }
                    }
                }
        }

        tags.observe(this) { pagingData ->
            tagAdapter.submitData(lifecycle, pagingData)
        }
    }

}