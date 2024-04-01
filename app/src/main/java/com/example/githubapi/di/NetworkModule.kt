package com.example.githubapi.di

import com.example.githubapi.BuildConfig
import com.example.source.api.GitHubApi
import com.example.source.api.adapter.DateTimeAdapter
import com.example.source.api.interceptor.HeaderInterceptor
import com.example.source.api.interceptor.HeaderInterceptor.Companion.AUTHORIZATION_HEADER
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addNetworkInterceptor(
                HttpLoggingInterceptor().apply {
                    if (BuildConfig.DEBUG) {
                        level = HttpLoggingInterceptor.Level.HEADERS
                        redactHeader(AUTHORIZATION_HEADER)
                    }
                },
            )
            .addInterceptor(HeaderInterceptor(BuildConfig.GITHUB_API_TOKEN))
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(DateTimeAdapter())
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(BuildConfig.API_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Singleton
    @Provides
    fun provideGitHubApi(retrofit: Retrofit): GitHubApi {
        return retrofit.create(GitHubApi::class.java)
    }
}
