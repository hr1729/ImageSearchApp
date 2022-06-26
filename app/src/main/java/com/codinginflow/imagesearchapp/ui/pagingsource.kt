package com.codinginflow.imagesearchapp.ui

import android.app.DownloadManager
import androidx.paging.PagingSource
import com.codinginflow.imagesearchapp.api.UnsplashPhoto
import com.codinginflow.imagesearchapp.api.services

class pagingsource(private val service: services, val query: String) :
    PagingSource<Int, UnsplashPhoto>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnsplashPhoto> {
        val pageNumber = params.key ?: 1
        return try {
            val response = service.getphotos(query,pageNumber,params.loadSize)
            val pagedResponse = response.body()
            val data = pagedResponse?.results

            LoadResult.Page(
                data = data.orEmpty(),
                prevKey = if (pageNumber == 1) null else pageNumber - 1,
                nextKey = if (data!!.isEmpty()) null else pageNumber + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}