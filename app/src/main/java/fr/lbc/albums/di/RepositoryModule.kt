package fr.lbc.albums.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fr.lbc.albums.data.local.AlbumsLocalDataSource
import fr.lbc.albums.data.local.AlbumsLocalDataSourceImpl
import fr.lbc.albums.data.remote.AlbumsRemoteDataSource
import fr.lbc.albums.data.remote.AlbumsRemoteDataSourceImpl
import fr.lbc.albums.data.repository.AlbumsRepository
import fr.lbc.albums.data.repository.AlbumsRepositoryImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@Suppress("unused")
@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @ExperimentalCoroutinesApi
    @Singleton
    @Binds
    abstract fun bindRepository(impl: AlbumsRepositoryImpl): AlbumsRepository

    @Singleton
    @Binds
    abstract fun bindRemoteDataSource(impl: AlbumsRemoteDataSourceImpl): AlbumsRemoteDataSource

    @Singleton
    @Binds
    abstract fun bindLocalDataSource(impl: AlbumsLocalDataSourceImpl): AlbumsLocalDataSource
}