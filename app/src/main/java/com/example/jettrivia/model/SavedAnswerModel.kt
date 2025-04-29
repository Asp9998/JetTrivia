package com.example.jettrivia.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_answer_tbl")
data class SavedAnswerModel(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "User_selected_ans")
    val ans: Int
)
