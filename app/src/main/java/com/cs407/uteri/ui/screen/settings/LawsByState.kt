package com.cs407.uteri.ui.screen.settings

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
        currentPolicy = "Abortion is legal until viability of the fetus",
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
        currentPolicy = "Abortion is legal until viability of the fetus",
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
        currentPolicy = "Abortion is legal until viability of the fetus",
        exceptions = "If there is a significant threat to the health of the pregnant person.",
        color = 3
    ),
    "Delaware" to LawsByState(
        legal = true,
        currentPolicy = "Abortion is legal until viability of the fetus",
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
        currentPolicy = "Abortion is legal until 6 weeks of pregnancy",
        exceptions = "If there is a significant threat to the health of the pregnant person, pregnancy resulting from rape or incest" +
                " (15 weeks), if the fetus will not survive (3rd trimester)",
        color = 2
    ),
    "Georgia" to LawsByState(
        legal = true,
        currentPolicy = "Abortion is legal until 6 weeks of pregnancy",
        exceptions = "If there is a significant threat to the health of the pregnant person, pregnancy resulting from rape or incest" +
                " (20 weeks), if the fetus will not survive",
        color = 2
    ),
    "Hawaii" to LawsByState(
        legal = true,
        currentPolicy = "Abortion is legal until viability of the fetus",
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
        currentPolicy = "Abortion is legal until viability of the fetus",
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
        currentPolicy = "Abortion is legal until 6 weeks of pregnancy",
        exceptions = "If there is a significant threat to the health of the pregnant person, pregnancy resulting from rape or incest" +
                " or if the fetus won't survive (20 weeks).",
        color = 2
    ),
    "Kansas" to LawsByState(
        legal = true,
        currentPolicy = "Abortion is legal until 22 weeks of pregnancy",
        exceptions = "If there is a significant threat to the health of the pregnant person.",
        color = 3
    ),
    "Kentucky" to LawsByState(
        legal = false,
        currentPolicy = "Abortion is completely banned",
        exceptions = "If there is a significant threat to the health of the pregnant person.",
        color = 1
    ),
    "Louisiana" to LawsByState(
        legal = false,
        currentPolicy = "Abortion is completely banned",
        exceptions = "If there is a significant threat to the health of the pregnant person.",
        color = 1
    ),






    // Add other states here...
)