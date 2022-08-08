package com.januzanj.sipmasdes.data.di

import android.content.Context
import android.content.SharedPreferences
import com.januzanj.sipmasdes.common.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object AppModule {

    @Provides
    @ViewModelScoped
    fun provideSharePreferences(
        @ApplicationContext context: Context
    ) : SharedPreferences{
        return context.getSharedPreferences(Constant.PREF_NAME, 0)
    }

    @Provides
    @ViewModelScoped
    fun provideContext(
        @ApplicationContext context: Context
    ) : Context{
        return context
    }

}