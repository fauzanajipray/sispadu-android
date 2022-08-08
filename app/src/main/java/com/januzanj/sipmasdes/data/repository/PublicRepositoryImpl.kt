package com.januzanj.sipmasdes.data.repository

import com.januzanj.sipmasdes.data.source.remote.network.PublicService
import com.januzanj.sipmasdes.data.source.remote.response.PositionsResponse
import com.januzanj.sipmasdes.domain.repository.PublicRepository
import javax.inject.Inject

class PublicRepositoryImpl @Inject constructor(
    private val publicService: PublicService
) : PublicRepository {

    override suspend fun getPositions(): PositionsResponse {
        return publicService.getPositions()
    }
}
