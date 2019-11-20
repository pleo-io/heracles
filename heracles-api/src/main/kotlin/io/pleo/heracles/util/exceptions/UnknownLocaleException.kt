package io.pleo.heracles.util.exceptions

class UnknownLocaleException(localeString: String) : Exception("Locale='$localeString' was not be found")
