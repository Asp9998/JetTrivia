package com.example.jettrivia.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.jettrivia.model.SavedAnswerModel

@Database(entities = [SavedAnswerModel::class], version = 1, exportSchema = false)
abstract class TriviaDatabase: RoomDatabase(){
    abstract fun triviaDao(): TriviaDatabaseDao
}