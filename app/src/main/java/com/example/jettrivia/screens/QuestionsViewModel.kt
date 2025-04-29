package com.example.jettrivia.screens

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jettrivia.data.DataOrException
import com.example.jettrivia.model.QuestionItem
import com.example.jettrivia.model.SavedAnswerModel
import com.example.jettrivia.repository.AnswerRepository
import com.example.jettrivia.repository.QuestionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionsViewModel @Inject constructor(private val questionRepository: QuestionRepository,
                                             private val answerRepository: AnswerRepository)  :
    ViewModel() {
    val data: MutableState<DataOrException<ArrayList<QuestionItem>, Boolean, Exception>> =
        mutableStateOf(DataOrException(null, true, Exception("")) )

    init {
        getAllQuestions()
    }

    private fun getAllQuestions(){
        viewModelScope.launch {
            data.value.loading = true
            data.value = questionRepository.getAllQuestion()
            if(data.value.data.toString().isNotEmpty()) data.value.loading = false
        }
    }

    fun totalQuestionCount(): Int{
        return data.value.data?.toMutableList()?.size!!
    }

    // Room database functions to save user selected answers

    private val _selectedAnswerState = MutableStateFlow<Int?>(null)  // Store state locally
    val selectedAnswerState: StateFlow<Int?> = _selectedAnswerState

    fun selectAnswer(questionIndex: Int, answerIndex: Int) {
        viewModelScope.launch {
            answerRepository.addUserAns(
                SavedAnswerModel(
                    id = questionIndex,
                    ans = answerIndex
                )
            )
        }
    }

    fun getSelectedAnswer(questionIndex: Int) {
        viewModelScope.launch {
            val answer = answerRepository.getUserAns(questionIndex)?.ans
            _selectedAnswerState.value = answer  // Update the state
        }
    }

    fun deleteAnsById(questionIndex: Int){
        viewModelScope.launch {
            answerRepository.deleteById(questionIndex)
        }
    }


}