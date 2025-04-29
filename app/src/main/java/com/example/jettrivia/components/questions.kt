package com.example.jettrivia.components

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jettrivia.model.QuestionItem
import com.example.jettrivia.screens.QuestionsViewModel
import com.example.jettrivia.util.AppColors

@Composable
fun Questions(viewModel: QuestionsViewModel){

    val questions = viewModel.data.value.data?.toMutableList()

    val questionIndex = remember {
        mutableStateOf(0)
    }

    val numberOfQuestions = 20
//    val numberOfQuestions = viewModel.totalQuestionCount()

    if (viewModel.data.value.loading == true){
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            CircularProgressIndicator()
        }
        Log.d("loading", "Questions: Loading....")
    }
    else{

        val question = try {
            questions?.get(questionIndex.value)
        }
        catch (ex: Exception){
            null
        }

        if(questions != null){
            QuestionDisplay(
                question = question!!,
                questionIndex = questionIndex,
                totalQuestions = numberOfQuestions,
                viewModel = viewModel,
                onPreviousClicked = {
                    if(questionIndex.value>=1){
                        questionIndex.value -= 1
                        viewModel.getSelectedAnswer(questionIndex.value)
                    }
                }
            ) {
                if (questionIndex.value < numberOfQuestions - 1){
                    questionIndex.value += 1
                    viewModel.getSelectedAnswer(questionIndex.value)
                }

            }
        }
    }
}

@Composable
fun QuestionDisplay(
    question: QuestionItem,
    questionIndex: MutableState<Int>,
    totalQuestions: Int,
    viewModel: QuestionsViewModel,
    onPreviousClicked: (Int) -> Unit,
    onNextClicked: (Int) -> Unit

){

    val choicesState = remember(question) {
        question.choices.toMutableList()
    }

    var selectedAnswer = viewModel.selectedAnswerState.collectAsState().value


    val ansState = remember(question) {
        mutableStateOf(selectedAnswer)
    }

    val correctAnswerState = remember(question) {
        mutableStateOf<Boolean?>(null)
    }

    val updateAnswer: (Int) -> Unit = remember(question) {
        {
            ansState.value = it
            correctAnswerState.value = choicesState[it] == question.answer
        }
    }

    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)

    Surface (modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(),
        color = AppColors.mWhite

    ) {

        Column (modifier = Modifier.padding(top = 40.dp, start = 32.dp, end = 32.dp, bottom = 60.dp)
            .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start
            ) {

            Column {

                QuestionTracker(counter = questionIndex.value+1, outOff = totalQuestions)
                DrawDottedLine(pathEffect)

                // Question
                Text(
                    text = question.question,
                    modifier = Modifier
                        .padding(top = 20.dp, bottom = 20.dp)
                        .align(alignment = Alignment.Start)
                        .fillMaxHeight(0.3f),
                    fontSize = 20.sp,
                    color = AppColors.mBlack,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 22.sp
                )

                // Choices
                choicesState.forEachIndexed { index, ansText ->
                    Row(
                        modifier = Modifier.padding(3.dp)
                            .fillMaxWidth()
                            .height(45.dp)
                            .border(
                                width = 2.dp,
                                brush = Brush.linearGradient(
                                    colors = listOf(
                                        AppColors.mLightBlue,
                                        AppColors.mLightBlue
                                    )
                                ),
                                shape = RoundedCornerShape(15.dp)
                            )
                            .clip(
                                RoundedCornerShape(
                                    topEndPercent = 50,
                                    topStartPercent = 50,
                                    bottomEndPercent = 50,
                                    bottomStartPercent = 50
                                )
                            )
                            .background(Color.Transparent),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // radio button start
                        RadioButton(
                            selected = (ansState.value == index),
                            onClick = {
                                updateAnswer(index)
                                viewModel.selectAnswer(
                                    questionIndex = questionIndex.value,
                                    answerIndex = index
                                )
                                      },
                            modifier = Modifier.padding(start = 16.dp),
                            colors = RadioButtonDefaults
                                .colors(
//                                    selectedColor =
//                                        if (correctAnswerState.value == true
//                                            && index == selectedAnswer) {
//                                            Color.Green.copy(alpha = 0.2f)
//                                        }
//                                        else {
//                                            Color.Red.copy(alpha = 0.2f)
//                                        }
                                )
                        )
                        // radio button ends

                        val annotatedString = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    fontWeight = FontWeight.SemiBold,
//                                    color =
//                                        if (correctAnswerState.value == true
//                                            && index == selectedAnswer) {
//                                            Color.Green
//                                            }
//                                        else if (correctAnswerState.value == false
//                                            && index == selectedAnswer) {
//                                            Color.Red
//                                        }
//                                        else {
//                                            AppColors.mOffWhite
//                                        }
                                )
                            ) {
                                append(ansText)
                            }
                        }

                        Text(text = annotatedString, modifier = Modifier.padding(6.dp))

                    }
                }

//                 Clear choice
                Text(
                    text = "Clear Choice",
                    color = Color.Black,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(top = 10.dp, start = 6.dp)
                        .clickable{
                            viewModel.deleteAnsById(questionIndex.value)  // Update database
                            ansState.value = null
                            correctAnswerState.value = null
                        }
                )

            }

            Column {
            //buttons
            Row (verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween){

                QuestionButton(
                    text = "Previous",
                    onClick = {onPreviousClicked(questionIndex.value)},
                    enabled = true,
                )

                QuestionButton(
                    text = "Next",
                    onClick = {onNextClicked(questionIndex.value)},
                    enabled = true,
                )
            }
                        }
        }
    }
}


@Composable
fun DrawDottedLine(pathEffect: PathEffect){
    Canvas(modifier = Modifier
        .fillMaxWidth()
        .height(1.dp)) {
        drawLine(color = AppColors.mBlue,
            start = Offset(0f, 0f),
            end = Offset(size.width, 0f),
            pathEffect = pathEffect
            )
    }

}

@Composable
fun QuestionTracker(counter: Int = 10,
                    outOff: Int = 100){
    Text(text = buildAnnotatedString {
        withStyle(style = ParagraphStyle(textIndent = TextIndent.None)){
            withStyle(style = SpanStyle(color = AppColors.mBlack,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 27.sp))
            {
                append("Question $counter/")
            }

            withStyle(style = SpanStyle(color = AppColors.mBlack,
                                        fontWeight = FontWeight.Normal,
                                        fontSize = 14.sp))
            {
                append("$outOff")
            }

        }
    }, modifier = Modifier.padding(top = 20.dp, bottom =20.dp))

}
