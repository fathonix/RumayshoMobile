package com.idn.rumayshomobile.utils.di

import com.idn.rumayshomobile.utils.network.WordpressApi
import com.idn.rumayshomobile.utils.repository.WordPressRepository

object Injection {

    fun provideWordpressRepository(): WordPressRepository {
        return WordPressRepository(WordpressApi.create())
    }

}