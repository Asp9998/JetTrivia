package com.example.jettrivia.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.jettrivia.model.SavedAnswerModel
import kotlinx.coroutines.flow.Flow

@Dao
interface TriviaDatabaseDao {

    @Query("SELECT * FROM user_answer_tbl")
    fun getAllUserSelectedAns(): Flow<List<SavedAnswerModel>>

    @Query("SELECT * FROM user_answer_tbl WHERE id = :questionIndex LIMIT 1")
    suspend fun getAnsById(questionIndex: Int): SavedAnswerModel?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(savedAnswerModel: SavedAnswerModel)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(savedAnswerModel: SavedAnswerModel)

    @Query("DELETE FROM user_answer_tbl WHERE id = :questionIndex")
    suspend fun deleteAnswerById(questionIndex: Int)

    @Query("DELETE FROM user_answer_tbl")
    suspend fun deleteAll()
}
