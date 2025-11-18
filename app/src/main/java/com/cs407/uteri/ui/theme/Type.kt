package com.cs407.uteri.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import com.cs407.uteri.R

private val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

val NunitoFontFamily = FontFamily(
    Font(GoogleFont("Nunito"), provider, FontWeight.Normal),
    Font(GoogleFont("Nunito"), provider, FontWeight.Medium),
    Font(GoogleFont("Nunito"), provider, FontWeight.SemiBold),
    Font(GoogleFont("Nunito"), provider, FontWeight.Bold)
)

// Helper function to apply font family to a TextStyle
private fun TextStyle.withFontFamily(fontFamily: FontFamily): TextStyle {
    return this.copy(fontFamily = fontFamily)
}

// Get Material3 default Typography and apply our font family globally
val Typography = Typography().let { defaultTypography ->
    Typography(
        displayLarge = defaultTypography.displayLarge.withFontFamily(NunitoFontFamily),
        displayMedium = defaultTypography.displayMedium.withFontFamily(NunitoFontFamily),
        displaySmall = defaultTypography.displaySmall.withFontFamily(NunitoFontFamily),
        headlineLarge = defaultTypography.headlineLarge.withFontFamily(NunitoFontFamily),
        headlineMedium = defaultTypography.headlineMedium.withFontFamily(NunitoFontFamily),
        headlineSmall = defaultTypography.headlineSmall.withFontFamily(NunitoFontFamily),
        titleLarge = defaultTypography.titleLarge.withFontFamily(NunitoFontFamily),
        titleMedium = defaultTypography.titleMedium.withFontFamily(NunitoFontFamily),
        titleSmall = defaultTypography.titleSmall.withFontFamily(NunitoFontFamily),
        bodyLarge = defaultTypography.bodyLarge.withFontFamily(NunitoFontFamily),
        bodyMedium = defaultTypography.bodyMedium.withFontFamily(NunitoFontFamily),
        bodySmall = defaultTypography.bodySmall.withFontFamily(NunitoFontFamily),
        labelLarge = defaultTypography.labelLarge.withFontFamily(NunitoFontFamily),
        labelMedium = defaultTypography.labelMedium.withFontFamily(NunitoFontFamily),
        labelSmall = defaultTypography.labelSmall.withFontFamily(NunitoFontFamily)
    )
}

