package com.cs407.uteri.data

import androidx.compose.ui.graphics.Color

data class LawsByState(
    val legal: Boolean = false,
    val currentPolicy: String = "",
    val exceptions: String = "",
    val color: Int = 0
)

// Color mapping: 1 = illegal. 2 = accessible, not protected. 3 = protected

fun getAbortionLawByState(state: String): LawsByState? {
    return abortionLawsByState[state] // returns null if not found
}

val abortionLawsByState = mapOf(
    "Alabama" to LawsByState(
        legal = false,
        currentPolicy = "Abortion is completely banned.",
        exceptions = "If there is a significant threat to the health of the pregnant person, if the fetus will not survive",
        color = 1
    ),
    "Alaska" to LawsByState(
        legal = true,
        currentPolicy = "Abortion is legal. There is no limit on how far along in pregnancy you are.",
        color = 3
    ),
    "Arizona" to LawsByState(
        legal = true,
        currentPolicy = "Abortion is legal until viability of the fetus.",
        exceptions = "If there is a significant threat to the health of the pregnant person.",
        color = 3
    ),
    "Arkansas" to LawsByState(
        legal = false,
        currentPolicy = "Abortion is completely banned.",
        exceptions = "If there is a threat to the life of the pregnant person,",
        color = 1
    ),
    "California" to LawsByState(
        legal = true,
        currentPolicy = "Abortion is legal until viability of the fetus.",
        exceptions = "If there is a significant threat to the health of the pregnant person.",
        color = 3
    ),
    "Colorado" to LawsByState(
        legal = true,
        currentPolicy = "Abortion is legal. There is no limit on how far along in pregnancy you are.",
        color = 3
    ),
    "Connecticut" to LawsByState(
        legal = true,
        currentPolicy = "Abortion is legal until viability of the fetus.",
        exceptions = "If there is a significant threat to the health of the pregnant person.",
        color = 3
    ),
    "Delaware" to LawsByState(
        legal = true,
        currentPolicy = "Abortion is legal until viability of the fetus.",
        exceptions = "If there is a significant threat to the health of the pregnant person, if the fetus will not survive",
        color = 3
    ),
    "District of Colombia" to LawsByState(
        legal = true,
        currentPolicy = "Abortion is legal. There is no limit on how far along in pregnancy you are.",
        color = 3
    ),
    "Florida" to LawsByState(
        legal = true,
        currentPolicy = "Abortion is legal until 6 weeks of pregnancy.",
        exceptions = "If there is a significant threat to the health of the pregnant person, pregnancy resulting from rape or incest" +
                " (15 weeks), if the fetus will not survive (3rd trimester)",
        color = 2
    ),
    "Georgia" to LawsByState(
        legal = true,
        currentPolicy = "Abortion is legal until 6 weeks of pregnancy.",
        exceptions = "If there is a significant threat to the health of the pregnant person, pregnancy resulting from rape or incest" +
                " (20 weeks), if the fetus will not survive",
        color = 2
    ),
    "Hawaii" to LawsByState(
        legal = true,
        currentPolicy = "Abortion is legal until viability of the fetus.",
        exceptions = "If there is a significant threat to the health of the pregnant person",
        color = 3
    ),
    "Idaho" to LawsByState(
        legal = false,
        currentPolicy = "Abortion is completely banned.",
        exceptions = "If there is a threat to the life of the pregnant person, pregnancy resulting from rape or incest" +
                " (first trimester)",
        color = 1
    ),
    "Illinois" to LawsByState(
        legal = true,
        currentPolicy = "Abortion is legal until viability of the fetus.",
        exceptions = "If there is a significant threat to the health of the pregnant person.",
        color = 3
    ),
    "Indiana" to LawsByState(
        legal = false,
        currentPolicy = "Abortion is completely banned.",
        exceptions = "If there is a significant threat to the health of the pregnant person or " +
                "the fetus won't survive (20 weeks), pregnancy resulting from rape or incest (10 weeks)",
        color = 1
    ),
    "Iowa" to LawsByState(
        legal = true,
        currentPolicy = "Abortion is legal until 6 weeks of pregnancy.",
        exceptions = "If there is a significant threat to the health of the pregnant person, pregnancy resulting from rape or incest" +
                " or if the fetus won't survive (20 weeks).",
        color = 2
    ),
    "Kansas" to LawsByState(
        legal = true,
        currentPolicy = "Abortion is legal until 22 weeks of pregnancy.",
        exceptions = "If there is a significant threat to the health of the pregnant person.",
        color = 3
    ),
    "Kentucky" to LawsByState(
        legal = false,
        currentPolicy = "Abortion is completely banned.",
        exceptions = "If there is a significant threat to the health of the pregnant person.",
        color = 1
    ),
    "Louisiana" to LawsByState(
        legal = false,
        currentPolicy = "Abortion is completely banned.",
        exceptions = "If there is a significant threat to the health of the pregnant person, or if the fetus won't survive.",
        color = 1
    ),
    "Maine" to LawsByState(
        legal = true,
        currentPolicy = "Abortion is legal until viability of the fetus.",
        exceptions = "If there is a threat to the life of the pregnant person.",
        color = 3
    ),
    "Maryland" to LawsByState(
        legal = true,
        currentPolicy = "Abortion is legal. There is no limit on how far along in pregnancy you are.",
        color = 3
    ),
    "Massachusetts" to LawsByState(
        legal = true,
        currentPolicy = "Abortion is legal until 24 weeks of pregnancy.",
        exceptions = "If there is a significant threat to the health of the pregnant person, or if the fetus won't survive.",
        color = 3
    ),
    "Michigan" to LawsByState(
        legal = true,
        currentPolicy = "Abortion is legal. There is no limit on how far along in pregnancy you are.",
        color = 3
    ),
    "Minnesota" to LawsByState(
        legal = true,
        currentPolicy = "Abortion is legal. There is no limit on how far along in pregnancy you are.",
        color = 3
    ),
    "Mississippi" to LawsByState(
        legal = false,
        currentPolicy = "Abortion is completely banned.",
        exceptions = "If there is a threat to the life of the pregnant person, pregnancy resulting from rape.",
        color = 1
    ),
    "Missouri" to LawsByState(
        legal = true,
        currentPolicy = "Abortion is legal until viability of the fetus.",
        exceptions = "If there is a significant threat to the health of the pregnant person.",
        color = 3
    ),
    "Montana" to LawsByState(
        legal = true,
        currentPolicy = "Abortion is legal until viability of the fetus.",
        exceptions = "If there is a significant threat to the health of the pregnant person.",
        color = 3
    ),
    "Nebraska" to LawsByState(
        legal = true,
        currentPolicy = "Abortion is legal until 12 weeks of pregnancy.",
        exceptions = "If there is a significant threat to the health of the pregnant person, pregnancy." +
                " resulting from rape or incest, if the fetus won't survive.",
        color = 2
    ),
    "Nevada" to LawsByState(
        legal = true,
        currentPolicy = "Abortion is legal until 24 weeks of pregnancy.",
        exceptions = "If there is a threat to the health of the pregnant person.",
        color = 3
    ),
    "New Hampshire" to LawsByState(
        legal = true,
        currentPolicy = "Abortion is legal. There is no limit on how far along in pregnancy you are.",
        color = 3
    ),
    "New Jersey" to LawsByState(
        legal = true,
        currentPolicy = "Abortion is legal. There is no limit on how far along in pregnancy you are.",
        color = 3
    ),
    "New Mexico" to LawsByState(
        legal = true,
        currentPolicy = "Abortion is legal. There is no limit on how far along in pregnancy you are.",
        color = 2
    ),
    "New York" to LawsByState(
        legal = true,
        currentPolicy = "Abortion is legal until 24 weeks of pregnancy or viability.",
        exceptions = "If there is a threat to the health of the pregnant person",
        color = 3
    ),
    "North Carolina" to LawsByState(
        legal = true,
        currentPolicy = "Abortion is legal until 12 weeks of pregnancy.",
        exceptions = "If there is a significant threat to the health of the pregnant person, " +
                "pregnancy resulting from rape or incest (20 weeks), or if the fetus won't survive (24 weeks).",
        color = 2
    ),
    "North Dakota" to LawsByState(
        legal = false,
        currentPolicy = "Abortion is completely banned.",
        exceptions = "If there is a significant threat to the health of the pregnant person, " +
                "pregnancy resulting from rape or incest (6 weeks).",
        color = 1
    ),
    "Ohio" to LawsByState(
        legal = true,
        currentPolicy = "Abortion is legal until 20 weeks of pregnancy.",
        exceptions = "If there is a significant threat to the health of the pregnant person.",
        color = 3
    ),
    "Oklahoma" to LawsByState(
        legal = false,
        currentPolicy = "Abortion is completely banned.",
        exceptions = "If there is a threat to the life of the pregnant person",
        color = 1
    ),
    "Oregon" to LawsByState(
        legal = true,
        currentPolicy = "Abortion is legal. There is no limit on how far along in pregnancy you are.",
        color = 3
    ),
    "Pennsylvania" to LawsByState(
        legal = true,
        currentPolicy = "Abortion is legal until 24 weeks of pregnancy.",
        exceptions = "If there is a significant threat to the health of the pregnant person.",
        color = 2
    ),
    "Rhode Island" to LawsByState(
        legal = true,
        currentPolicy = "Abortion is legal until viability of the fetus.",
        exceptions = "If there is a threat to the health of the pregnant person.",
        color = 3
    ),
    "South Carolina" to LawsByState(
        legal = true,
        currentPolicy = "Abortion is legal until 6 weeks of pregnancy.",
        exceptions = "If there is a significant threat to the health of the pregnant person, " +
                "pregnancy resulting from rape or incest (12 weeks), or if the fetus won't survive.",
        color = 2
    ),
    "South Dakota" to LawsByState(
        legal = false,
        currentPolicy = "Abortion is completely banned.",
        exceptions = "If there is a threat to the life of the pregnant person.",
        color = 1
    ),
    "Tennessee" to LawsByState(
        legal = false,
        currentPolicy = "Abortion is completely banned.",
        exceptions = "If there is a significant threat to the health of the pregnant person.",
        color = 1
    ),
    "Texas" to LawsByState(
        legal = false,
        currentPolicy = "Abortion is completely banned.",
        exceptions = "If there is a significant threat to the health of the pregnant person.",
        color = 1
    ),
    "Utah" to LawsByState(
        legal = true,
        currentPolicy = "Abortion is legal until 18 weeks of pregnancy.",
        exceptions = "If there is a significant threat to the health of the pregnant person, or" +
                " if the fetus won't survive.",
        color = 2
    ),
    "Vermont" to LawsByState(
        legal = true,
        currentPolicy = "Abortion is legal. There is no limit on how far along in pregnancy you are.",
        color = 2
    ),
    "Virginia" to LawsByState(
        legal = true,
        currentPolicy = "Abortion is legal until the 3rd trimester.",
        exceptions = "If there is a threat to the health of the pregnant person.",
        color = 2
    ),
    "Washington" to LawsByState(
        legal = true,
        currentPolicy = "Abortion is legal until viability of the fetus.",
        exceptions = "If there is a threat to the health of the pregnant person.",
        color = 3
    ),
    "West Virginia" to LawsByState(
        legal = false,
        currentPolicy = "Abortion is completely banned.",
        exceptions = "If there is a significant threat to the health of the pregnant person, pregnancy resulting from rape or incest" +
                " (8 weeks), if the fetus will not survive",
        color = 2
    ),
    "Wisconsin" to LawsByState(
        legal = true,
        currentPolicy = "Abortion is legal until 20 weeks of pregnancy.",
        exceptions = "If there is a significant threat to the health of the pregnant person.",
        color = 2
    ),
    "Wyoming" to LawsByState(
        legal = true,
        currentPolicy = "Abortion is legal until viability of the fetus.",
        exceptions = "If there is a threat to the health of the pregnant person.",
        color = 2
    ),
)

fun getColorFromCode(code: Int): Color {
    return when (code) {
        1 -> Color(0xFFFF6B6B) // red
        2 -> Color(0xFFFFD93D) // yellow
        3 -> Color(0xFF6BCB77) // green
        else -> Color.LightGray
    }
}