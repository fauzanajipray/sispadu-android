package com.devajip.sispadu.domain.use_case.user

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.devajip.sispadu.common.Constant
import com.devajip.sispadu.data.source.remote.response.ComplaintsRequestParams
import com.devajip.sispadu.domain.model.Complaint
import com.devajip.sispadu.domain.repository.ComplaintRepository
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