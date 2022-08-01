package com.januzanj.sipmasdes.data.di

import com.januzanj.sipmasdes.data.repository.AuthRepositoryImpl
import com.januzanj.sipmasdes.data.repository.ComplaintRepositoryImpl
import com.januzanj.sipmasdes.data.source.remote.network.AuthService
import com.januzanj.sipmasdes.data.source.remote.network.UserComplaintService
import com.januzanj.sipmasdes.domain.repository.AuthRepository
import com.januzanj.sipmasdes.domain.repository.ComplaintRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideAuthRepository(
        authService: AuthService
    ): AuthRepository {
        return AuthRepositoryImpl(authService)
    }

    @Provides
    @Singleton
    fun provideComplaintRepository(
        complaintService: UserComplaintService
    ): ComplaintRepository {
        return ComplaintRepositoryImpl(complaintService)
    }


}