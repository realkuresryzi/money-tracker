package com.example.moneytracker.feature_transaction.presentation.util

import java.util.Locale

fun getNominativeMonth(month: String, locale: Locale): String {
    // Example for Czech language (cs)
    return if (locale.language == "cs") {
        when (month.lowercase(locale)) {
            "ledna" -> "Leden"
            "února" -> "Únor"
            "března" -> "Březen"
            "dubna" -> "Duben"
            "května" -> "Květen"
            "června" -> "Červen"
            "července" -> "Červenec"
            "srpna" -> "Srpen"
            "září" -> "Září"
            "října" -> "Říjen"
            "listopadu" -> "Listopad"
            "prosince" -> "Prosinec"
            else -> month
        }
    } else {
        month
    }
}