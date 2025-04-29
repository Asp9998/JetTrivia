package com.example.jettrivia.di

import android.content.Context
import androidx.room.Room
import com.example.jettrivia.data.TriviaDatabase
import com.example.jettrivia.network.QuestionApi
import com.example.jettrivia.repository.QuestionRepository
import com.example.jettrivia.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // Retrofit Api Instances

    @Singleton
    @Provides
    fun provideQuestionApi(): QuestionApi{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(QuestionApi::class.java)
    }

    @Singleton
    @Provides
    fun provideQuestionRepository(api: QuestionApi) = QuestionRepository(api)


    // Room Database Instances

    @Singleton
    @Provides
    fun provideTriviaDatabase(@ApplicationContext context: Context): TriviaDatabase
            = Room.databaseBuilder(context,
        TriviaDatabase::class.java,
        name = "notes_db")
        .fallbackToDestructiveMigration(false)
        .build()

    @Singleton
    @Provides
    fun provideTriviaDatabaseDao(triviaDatabase: TriviaDatabase) =
        triviaDatabase.triviaDao()


}