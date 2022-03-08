package com.devajip.sispadu.data.di

import com.devajip.sispadu.data.repository.AuthRepositoryImpl
import com.devajip.sispadu.data.source.remote.network.AuthService
import com.devajip.sispadu.domain.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    fun provideAuthRepository(
        authService: AuthService
    ): AuthRepository {
        return AuthRepositoryImpl(authService)
    }
}