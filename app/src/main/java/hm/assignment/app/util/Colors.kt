package hm.assignment.app.util

import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

/**
 * Created on 2022-03-06.
 * CopyrightⒸ Kagge
 */

object Colors {
    private val appBackground = Color(0xFF0F1923)
    private val red = Color(0xFFCC274A)
    private val ebonyItem = Color(0xFF13212E)
    private val ebonySecondaryVariant = Color(0xFF3477B5)
    private val ebonyPurple = Color(0xFF303674)

    val EbonyColors = lightColors(
        background = appBackground,
        primary = ebonyPurple,
        primaryVariant = ebonyItem,
        secondary = red,
        secondaryVariant = ebonySecondaryVariant
    )
}