package io.pleo.heracles.application.util

import io.pleo.heracles.application.exceptions.UnknownLocaleException
import java.util.Locale
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class LocalizerTest {

    @Nested
    inner class ResolveLocale {
        @Test
        fun `test valid full locale resolved`() {
            assertDoesNotThrow {
                assert(Localizer.resolveLocale("en_UK") == Locale.ENGLISH)
            }
        }

        @Test
        fun `test valid partial locale resolved accurately`() {
            assertDoesNotThrow {
                assert(Localizer.resolveLocale("en") == Locale.ENGLISH)
            }
        }

        @Test
        fun `test invalid locale throws UnknownLocaleException`() {
            assertThrows<UnknownLocaleException> {
                Localizer.resolveLocale("unknown")
            }
        }
    }
}
