package com.januzanj.sipmasdes.domain.repository

import com.januzanj.sipmasdes.data.source.remote.response.PositionsResponse

interface PublicRepository {
    suspend fun getPositions(): PositionsResponse
}