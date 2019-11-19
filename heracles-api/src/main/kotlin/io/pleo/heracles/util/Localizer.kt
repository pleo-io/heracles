package io.pleo.heracles.util

import io.pleo.heracles.util.exceptions.UnknownLocaleException
import java.util.Locale
import kotlin.collections.HashMap

object Localizer {

    // Map exists as a cache in order to resolve locales faster.
    // it also stores the cached string against the Locale used, as these can be
    // different.
    // Eg the user may supply "en_KE", but the closest locale found is just en.
    // the map will map the "en_KE" string to the en locale.
    private val localeMap: Map<String, Locale> = HashMap()
    /**
     * Looks up the Locale based on String. This method acts as a hook to return a
     * custom locale should it be required. Eg: if No locale 'en_XX' exists, we
     * could decide to return 'en' as a reasonable default. This method could be
     * changed to apply a default locale instead of throwing an exception.
     *
     * @param localeString
     * @return
     * @throws UnknownLocaleException
     */
    @Throws(UnknownLocaleException::class)
     fun resolveLocale(localeString: String): Locale? {
        val casedLocale = localeString.toLowerCase()
        // first see if it exists in the cached map
        val locale: Locale? = localeMap[casedLocale]
        if (locale != null) return locale

        // try match on the full locale eg: en_UK
        var result = findLocale(casedLocale)
        if (result != null) {
            localeMap.plus(Pair(casedLocale, result))
        }
        // try match on only the language eg: en
        val dashIndex = casedLocale.indexOf("_")
        if (dashIndex != -1) {
            result = findLocale(casedLocale.substring(0, dashIndex))
            if (result != null) {
                localeMap.plus(Pair(casedLocale, result))
                return result
            }
        }
        throw UnknownLocaleException(localeString)
    }

    private fun findLocale(casedLocale: String): Locale? {
        return Locale.getAvailableLocales().first { knownLocale ->
            knownLocale.toString().equals(other = casedLocale, ignoreCase = true)
        }
    }
}
