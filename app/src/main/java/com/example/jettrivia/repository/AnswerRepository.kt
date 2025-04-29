package com.example.jettrivia.repository

import androidx.room.Index
import com.example.jettrivia.data.TriviaDatabaseDao
import com.example.jettrivia.model.SavedAnswerModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AnswerRepository @Inject constructor(private val triviaDatabaseDao: TriviaDatabaseDao) {
    suspend fun getUserAns(id: Int) = triviaDatabaseDao.getAnsById(id)
    suspend fun addUserAns(savedAnswerModel: SavedAnswerModel) = triviaDatabaseDao.insert(savedAnswerModel)
    suspend fun updateUserAns(savedAnswerModel: SavedAnswerModel) = triviaDatabaseDao.update(savedAnswerModel)
    suspend fun deleteById(questionIndex: Int) = triviaDatabaseDao.deleteAnswerById(questionIndex)
    suspend fun deleteAllUserAns() = triviaDatabaseDao.deleteAll()
    fun getAllUserAns() = triviaDatabaseDao.getAllUserSelectedAns().flowOn(Dispatchers.IO).conflate()


}