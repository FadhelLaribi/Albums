package fr.lbc.albums.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fr.lbc.albums.data.local.AlbumLocalDataSource
import fr.lbc.albums.data.local.AlbumLocalDataSourceImpl
import fr.lbc.albums.data.remote.AlbumRemoteDataSource
import fr.lbc.albums.data.remote.AlbumRemoteDataSourceImpl
import fr.lbc.albums.data.repository.AlbumRepository
import fr.lbc.albums.data.repository.AlbumRepositoryImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@Suppress("unused")
@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindRepository(impl: AlbumRepositoryImpl): AlbumRepository

    @Singleton
    @Binds
    abstract fun bindRemoteDataSource(impl: AlbumRemoteDataSourceImpl): AlbumRemoteDataSource

    @Singleton
    @Binds
    abstract fun bindLocalDataSource(impl: AlbumLocalDataSourceImpl): AlbumLocalDataSource
}