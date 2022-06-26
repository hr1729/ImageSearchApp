package com.codinginflow.imagesearchapp.ui

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.codinginflow.imagesearchapp.api.services
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class repository @Inject constructor(private val res:services) {
    fun getSearchResults(query: String) =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { pagingsource(res, query) }
        ).liveData
}