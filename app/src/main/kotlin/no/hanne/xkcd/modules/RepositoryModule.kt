package no.hanne.xkcd.modules

import android.app.Application
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import no.hanne.xkcd.core.repository.ComicRepository
import no.hanne.xkcd.core.repository.ComicRepositoryImpl
import no.hanne.xkcd.core.repository.DatastoreRepository
import no.hanne.xkcd.core.repository.DatastoreRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindComicRepository(
        repo: ComicRepositoryImpl
    ): ComicRepository
}

@Module
@InstallIn(SingletonComponent::class)
class RepoArtifactModule {
    @Singleton
    @Provides
    fun provideDatastoreRepository(
        application: Application
    ): DatastoreRepository =
        DatastoreRepositoryImpl(
            application = application,
            dataStoreFilename = DataStoreFilenames.APP_SETTINGS
        )
    private object DataStoreFilenames {
        const val APP_SETTINGS = "APP_SETTINGS"
    }
}
