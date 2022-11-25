package no.hanne.xkcd.modules

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import no.hanne.xkcd.core.repository.ComicRepository
import no.hanne.xkcd.core.repository.ComicRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindComicRepository(
        repo: ComicRepositoryImpl
    ): ComicRepository
}
