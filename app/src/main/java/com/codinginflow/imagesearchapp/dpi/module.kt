package com.codinginflow.imagesearchapp.dpi

import com.codinginflow.imagesearchapp.api.services
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object module {
    @Provides
    @Singleton
    fun provideRetroift():Retrofit=
        Retrofit.Builder().baseUrl(services.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
@Provides
@Singleton
fun provideclass(re:Retrofit):services=re.create(services::class.java)
}