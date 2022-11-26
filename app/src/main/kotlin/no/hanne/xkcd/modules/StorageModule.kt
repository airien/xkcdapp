package no.hanne.xkcd.modules

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import no.hanne.xkcd.core.database.XKCDDatabase
import no.hanne.xkcd.core.database.dao.ComicDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class StorageModule {

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
