package com.oluwafemi.cardxplorer.di

import com.oluwafemi.cardxplorer.data.Repository
import com.oluwafemi.cardxplorer.data.RepositoryImpl
import com.oluwafemi.cardxplorer.data.remote.CardService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Provides
    @Singleton
    fun provideRepository(cardService: CardService, dispatcher: CoroutineDispatcher): Repository =
        RepositoryImpl(cardService, dispatcher)

}