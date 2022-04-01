package fr.lbc.albums

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import fr.lbc.albums.data.local.db.AppDatabase
import javax.inject.Qualifier

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Qualifier
    annotation class TestDatabase

    @TestDatabase
    @Provides
    fun provideInMemoryDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }
}