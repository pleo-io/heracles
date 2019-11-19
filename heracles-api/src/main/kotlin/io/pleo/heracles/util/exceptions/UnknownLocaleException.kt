package io.pleo.heracles.util.exceptions

class UnknownLocaleException(localeString: String) : Exception("Locale='$localeString' could not be found")
