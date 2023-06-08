package com.idn.rumayshomobile.utils.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.idn.rumayshomobile.models.post.Posts
import com.idn.rumayshomobile.models.collection.Collection
import com.idn.rumayshomobile.utils.di.Injection
import com.idn.rumayshomobile.utils.repository.WordPressRepository

class SharedViewModel : ViewModel() {

    private val wordPressRepository: WordPressRepository = Injection.provideWordpressRepository()

    fun getPosts() : LiveData<PagingData<Posts>> {
        return wordPressRepository.getPosts().cachedIn(viewModelScope)
    }

    fun getPostByCategory(categoryId: Int) : LiveData<PagingData<Posts>> {
        return wordPressRepository.getPostByCategory(categoryId).cachedIn(viewModelScope)
    }

    fun getPostByTag(tagId: Int) : LiveData<PagingData<Posts>> {
        return wordPressRepository.getPostByTag(tagId).cachedIn(viewModelScope)
    }

    fun getCategories() : LiveData<PagingData<Collection>> {
        return wordPressRepository.getCategories().cachedIn(viewModelScope)
    }

    fun getTags() : LiveData<PagingData<Collection>> {
        return wordPressRepository.getTags().cachedIn(viewModelScope)
    }

    fun searchPost(keyword: String): LiveData<PagingData<Posts>> {
        return wordPressRepository.searchPost(keyword).cachedIn(viewModelScope)
    }

}