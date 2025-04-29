package com.example.jettrivia.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jettrivia.util.AppColors

@Composable
fun QuestionButton(modifier: Modifier = Modifier,
                   text: String,
                   onClick: () -> Unit,
                   enabled: Boolean = true){

        Button(
            modifier = modifier.padding(3.dp).width(124.dp),
            onClick = onClick,
            enabled = enabled,
            shape = RoundedCornerShape(15.dp),
            colors = buttonColors(containerColor = AppColors.mLightBlue)


        ) {
            Text(
                text = text,
                modifier = Modifier.padding(4.dp),
                color = AppColors.mButtonTextColor,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold


            )
        }
}