package fr.lbc.albums.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import fr.lbc.albums.data.local.db.AppDatabase
import javax.inject.Qualifier
import javax.inject.Singleton

@Suppress("unused")
@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Qualifier
    annotation class ActualDatabase

    @ActualDatabase
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, AppDatabase::class.java, "db_albums").build()

    @Provides
    fun provideAlbumsDao(@ActualDatabase database: AppDatabase) = database.albumDao()

}