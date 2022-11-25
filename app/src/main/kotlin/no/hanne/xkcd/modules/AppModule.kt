package no.hanne.xkcd.modules

import android.app.Application
import android.content.Context
import android.content.res.Resources
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton
import no.hanne.xkcd.util.TickerFlower

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideTickerFlower(): TickerFlower = TickerFlower()

    @Provides
    fun provideApplicationContext(application: Application): Context? = application.applicationContext

    @Provides
    @Named("applicationContext")
    fun provideNamedApplicationContext(application: Application): Context? = application.applicationContext

    @Provides
    fun provideResources(
        application: Application
    ): Resources {
        return application.resources
    }
}
