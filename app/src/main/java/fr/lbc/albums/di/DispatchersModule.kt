package fr.lbc.albums.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Suppress("unused")
@InstallIn(SingletonComponent::class)
@Module
object DispatchersModule {

    @Singleton
    @Provides
    fun provideDispatcherIO() = Dispatchers.IO

}