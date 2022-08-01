package com.januzanj.sipmasdes.data.di

import com.januzanj.sipmasdes.data.source.remote.network.AuthService
import com.januzanj.sipmasdes.common.Constant
import com.januzanj.sipmasdes.data.source.remote.network.StaffComplaintService
import com.januzanj.sipmasdes.data.source.remote.network.UserComplaintService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL = "http://ffb8-103-105-35-115.ngrok.io/api/"

    @Provides
    @Singleton
    fun provideRetrofit() : Retrofit {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl(Constant.BASE_URL_API)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthService(retrofit: Retrofit) : AuthService {
        return retrofit.create(AuthService::class.java)
    }

    @Provides
    @Singleton
    fun provideUserComplaintService(retrofit: Retrofit) : UserComplaintService {
        return retrofit.create(UserComplaintService::class.java)
    }

    @Provides
    @Singleton
    fun provideStaffComplaintService(retrofit: Retrofit) : StaffComplaintService {
        return retrofit.create(StaffComplaintService::class.java)
    }

}