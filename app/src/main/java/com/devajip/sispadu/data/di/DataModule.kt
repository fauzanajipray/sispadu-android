package com.devajip.sispadu.data.di

import com.devajip.sispadu.data.repository.AuthRepositoryImpl
import com.devajip.sispadu.data.repository.ComplaintRepositoryImpl
import com.devajip.sispadu.data.source.remote.network.AuthService
import com.devajip.sispadu.data.source.remote.network.UserComplaintService
import com.devajip.sispadu.domain.repository.AuthRepository
import com.devajip.sispadu.domain.repository.ComplaintRepository
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