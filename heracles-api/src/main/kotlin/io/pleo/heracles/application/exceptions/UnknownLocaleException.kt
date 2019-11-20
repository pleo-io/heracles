package io.pleo.heracles.application.exceptions

class UnknownLocaleException(localeString: String) : Exception("Locale='$localeString' was not be found")
