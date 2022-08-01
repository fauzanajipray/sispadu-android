package com.januzanj.sipmasdes.domain.use_case.user

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.januzanj.sipmasdes.common.Constant
import com.januzanj.sipmasdes.data.source.remote.response.ComplaintsRequestParams
import com.januzanj.sipmasdes.domain.model.Complaint
import com.januzanj.sipmasdes.domain.repository.ComplaintRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetComplaintPagingUseCase @Inject constructor(
    private val complaintRepository: ComplaintRepository
) {
    operator fun invoke(
        token: String,
        query: ComplaintsRequestParams
    ): Flow<PagingData<Complaint>> = Pager(PagingConfig(pageSize = 20)) {
        val tokenWithPrefix = Constant.TOKEN_PREFIX + token
        ComplaintSource(complaintRepository, tokenWithPrefix, query)
    }.flow
}