package no.hanne.xkcd.core.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
object TypographyConstants {
    const val oOne = 0.01
    const val oTwo = -0.02
    const val oThree = 0.03
}
val Typography = Typography(
    h1 = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 28.sp,
        lineHeight = 37.sp
    ),
    h2 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 33.sp,
        letterSpacing = TypographyConstants.oTwo.sp
    ),
    h3 = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
        letterSpacing = TypographyConstants.oOne.sp,
        lineHeight = 30.sp
    ),
    h4 = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 36.sp,
        letterSpacing = TypographyConstants.oTwo.sp,
        lineHeight = 44.sp
    ),
    h5 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        letterSpacing = TypographyConstants.oTwo.sp,
        lineHeight = 41.sp
    ),
    h6 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 32.sp,
        letterSpacing = TypographyConstants.oTwo.sp,
        lineHeight = 41.sp
    ),
    body1 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 17.sp,
        letterSpacing = TypographyConstants.oOne.sp,
        lineHeight = 20.sp
    ),
    body2 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp,
        letterSpacing = TypographyConstants.oOne.sp,
        lineHeight = 17.sp
    ),
    subtitle1 = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 15.sp,
        letterSpacing = TypographyConstants.oTwo.sp,
        lineHeight = 19.sp
    ),
    subtitle2 = TextStyle(
        fontWeight = FontWeight.Light,
        fontSize = 12.sp,
        letterSpacing = TypographyConstants.oTwo.sp,
        fontStyle = FontStyle.Normal,
        lineHeight = 15.sp
    ),
    caption = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 13.sp,
        lineHeight = 17.sp,
        letterSpacing = TypographyConstants.oOne.sp,
    ),
    button = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        letterSpacing = TypographyConstants.oThree.sp,
        fontFeatureSettings = "c2sc, smcp"
    )
)