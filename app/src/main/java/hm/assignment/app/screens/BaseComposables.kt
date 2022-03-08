package hm.assignment.app.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import hm.assignment.app.R
import hm.assignment.app.util.Colors

/**
 * Created on 2022-03-06.
 * CopyrightⒸ Kagge
 */


@Composable
fun Loading() {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(Modifier.height(50.dp))
        Text(stringResource(id = R.string.loading))
        Spacer(Modifier.height(16.dp))
        CircularProgressIndicator()
    }
}

@Preview
@Composable
fun ErrorScreen(title: String = "", desc: String = "", onTryAgain: () -> Unit = {}) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Colors.EbonyColors.background)
    ) {
        val (titleConstraint, descConstraint, tryAgainConstraint) = createRefs()
        createVerticalChain(titleConstraint, descConstraint, tryAgainConstraint, chainStyle = ChainStyle.Packed)

        Text(
            text = title,
            color = Color.White,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.constrainAs(titleConstraint) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }.width(350.dp)
        )
        Text(
            text = desc,
            color = Color.White,
            textAlign = TextAlign.Center,
            fontSize = 15.sp,
            modifier = Modifier.padding(top = 8.dp).constrainAs(descConstraint) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }.width(350.dp)
        )
        Button(
            onClick = { onTryAgain.invoke() },
            modifier = Modifier.padding(top = 8.dp).constrainAs(tryAgainConstraint) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        ) {
            Text(
                stringResource(id = R.string.try_again)
            )
        }
    }
}