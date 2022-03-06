package hm.assignment.app.screens.welcome

import androidx.compose.animation.*
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import hm.assignment.app.R
import hm.assignment.app.navigation.Screen
import hm.assignment.app.util.Colors
import hm.assignment.app.util.log

/**
 * Created on 2022-03-06.
 * CopyrightⒸ Kagge
 */

@Preview
@Composable
fun WelcomeScreen(navController: NavController? = null) {
    val animationSpeed = 1000
    val assignmentSpeed = animationSpeed * 2
    val delay = 500
    val horizontalOffset = 1000
    val verticalOffset = -1000

    val state = remember {
        MutableTransitionState(false).apply {
            targetState = true
        }
    }
    val buttonVisible = remember { mutableStateOf(true) }
    val navigate = remember { mutableStateOf(false) }

    if(!state.currentState && !state.targetState && !navigate.value) {
        navigate.value = true
        navController?.navigate(Screen.Countries.route)
        return
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(30.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom
        ) {
            AnimatedVisibility(
                visibleState = state,
                enter = fadeIn(tween(animationSpeed, delayMillis = delay)) +
                        slideInHorizontally (
                            animationSpec = tween(
                                animationSpeed,
                                delayMillis = delay
                            ),
                            initialOffsetX = { -horizontalOffset }
                        ),
                exit = slideOutHorizontally(
                    animationSpec = tween(animationSpeed),
                    targetOffsetX = { -horizontalOffset }
                )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.hm_h),
                    tint = Colors.EbonyColors.secondary,
                    contentDescription = "H")
            }

            AnimatedVisibility(
                visibleState = state,
                enter = fadeIn(tween(animationSpeed, delayMillis = delay + 250)) +
                        slideInVertically (
                            animationSpec = tween(
                                animationSpeed,
                                delayMillis = delay + 250
                            ),
                            initialOffsetY = { verticalOffset }
                        ),
                exit = slideOutVertically (
                    animationSpec = tween(animationSpeed),
                    targetOffsetY = { verticalOffset }
                )
            ) {
                Icon(
                    modifier = Modifier.padding(end = 20.dp),
                    painter = painterResource(id = R.drawable.hm_and),
                    tint = Colors.EbonyColors.secondary,
                    contentDescription = "H")
            }

            AnimatedVisibility(
                visibleState = state,
                enter = fadeIn(tween(animationSpeed, delayMillis = delay)) +
                        slideInHorizontally (
                            animationSpec = tween(
                                animationSpeed,
                                delayMillis = delay
                            ),
                            initialOffsetX = { horizontalOffset }
                        ),
                exit = slideOutHorizontally(
                    animationSpec = tween(animationSpeed),
                    targetOffsetX = { horizontalOffset }
                )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.hm_m),
                    tint = Colors.EbonyColors.secondary,
                    contentDescription = "H")
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            AnimatedVisibility(
                visibleState = state,
                enter = fadeIn(tween(assignmentSpeed, delayMillis = delay)) +
                        slideInVertically (
                            animationSpec = tween(
                                assignmentSpeed,
                                delayMillis = delay * 2
                            ),
                            initialOffsetY = { -verticalOffset * 3 }
                        ),
                exit = slideOutVertically (
                    animationSpec = tween(assignmentSpeed),
                    targetOffsetY = { -verticalOffset * 3 }
                )
            ) {
                Icon(
                    modifier = Modifier.size(width = 300.dp, height = 80.dp),
                    painter = painterResource(id = R.drawable.assignment),
                    tint = Colors.EbonyColors.secondaryVariant,
                    contentDescription = "Assignment")
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            AnimatedVisibility(
                visible = buttonVisible.value,
                exit = fadeOut(tween(animationSpeed))
            ) {
                Button(
                    onClick = {
                        state.targetState = false
                        buttonVisible.value = false
                    }
                ){
                    Text(text = stringResource(id = R.string.welcome))
                }
            }
        }
    }
}

@Composable
fun WelcomeCard() {
    Card(
        modifier = Modifier.padding(30.dp),
        backgroundColor = Color.LightGray
    ) {
        Text(
            text = "Im a little button",
            modifier = Modifier.padding(30.dp)
        )
    }
}