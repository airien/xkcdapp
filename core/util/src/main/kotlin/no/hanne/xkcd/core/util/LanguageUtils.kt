package no.hanne.xkcd.core.util

import java.util.Locale

object LanguageUtils {
    val languageLocaleCode: String
        get() = Locale.getDefault().language
    val languageCode: String
        get() {
            val languageLocale = languageLocaleCode
            return when {
                languageLocale.lowercase(Locale.ROOT).startsWith("en") -> PREFERRED_LANGUAGE_EN
                languageLocale.lowercase(Locale.ROOT).startsWith("fi") -> PREFERRED_LANGUAGE_FI
                languageLocale.lowercase(Locale.ROOT).startsWith("nb") -> PREFERRED_LANGUAGE_NO
                languageLocale.lowercase(Locale.ROOT).startsWith("nn") -> PREFERRED_LANGUAGE_NO
                languageLocale.lowercase(Locale.ROOT).startsWith("sv") -> PREFERRED_LANGUAGE_SV
                languageLocale.lowercase(Locale.ROOT).startsWith("da") -> PREFERRED_LANGUAGE_DA
                else -> PREFERRED_LANGUAGE_EN
            }
        }
    const val PREFERRED_LANGUAGE_NO = "nb"
    const val PREFERRED_LANGUAGE_FI = "fi"
    const val PREFERRED_LANGUAGE_SV = "sv"
    const val PREFERRED_LANGUAGE_DA = "da"
    const val PREFERRED_LANGUAGE_EN = "en"
}
