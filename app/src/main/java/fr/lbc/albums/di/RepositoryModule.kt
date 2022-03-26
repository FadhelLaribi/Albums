package fr.lbc.albums.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fr.lbc.albums.data.remote.AlbumRemoteDataSource
import fr.lbc.albums.data.remote.AlbumRemoteDataSourceImpl
import fr.lbc.albums.data.repository.AlbumRepository
import fr.lbc.albums.data.repository.AlbumRepositoryImpl
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindRepository(impl: AlbumRepositoryImpl): AlbumRepository

    @Singleton
    @Binds
    abstract fun bindRemoteDataSource(impl: AlbumRemoteDataSourceImpl): AlbumRemoteDataSource
}