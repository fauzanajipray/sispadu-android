package com.januzanj.sipmasdes.domain.use_case.user

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.januzanj.sipmasdes.data.source.remote.response.ComplaintsRequestParams
import com.januzanj.sipmasdes.domain.model.Complaint
import com.januzanj.sipmasdes.domain.repository.ComplaintRepository

class ComplaintSource (
    private val complaintRepository: ComplaintRepository,
    val token: String,
    private val query: ComplaintsRequestParams
) : PagingSource<Int, Complaint>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Complaint> {
        return try{
//            val page = params.key ?: 0
//            val nextPage = page + 1
            val nextPage = params.key ?: 1
            val response = complaintRepository.getComplaints(token, query, nextPage)
            LoadResult.Page(
                data = response.data.data,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (response.data.data.isEmpty()) null else nextPage + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Complaint>): Int? {
        return null
    }
}