package no.hanne.xkcd.modules

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import no.hanne.xkcd.core.database.XKCDDatabase
import no.hanne.xkcd.core.database.dao.ComicDao

@Module
@InstallIn(SingletonComponent::class)
class StorageModule {
    @Provides
    fun provideSharedPreferences(application: Application): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(application)
    }
    @Singleton
    @Provides
    fun provideDatabase(application: Application): XKCDDatabase {
        return XKCDDatabase(application)
    }

    @Singleton
    @Provides
    fun provideComicDao(db: XKCDDatabase): ComicDao {
        return db.comicDao()
    }
}
