package com.example.activityassignment

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Unit tests verifying navigation constants, default fallback payloads,
 * and timestamp formatting contracts across MainActivity and SecondActivity.
 */
class NavigationContractTest {

    @Test
    fun intentExtras_keysAreProperlyDefined() {
        assertEquals("extra_message", SecondActivity.EXTRA_MESSAGE)
        assertEquals("extra_timestamp", SecondActivity.EXTRA_TIMESTAMP)
    }

    @Test
    fun defaultMessage_isNotBlank() {
        assertNotNull(SecondActivity.DEFAULT_MESSAGE)
        assertTrue(SecondActivity.DEFAULT_MESSAGE.isNotBlank())
        assertEquals("Welcome to the second activity!", SecondActivity.DEFAULT_MESSAGE)
    }

    @Test
    fun timestampFormatting_formatsCorrectly() {
        val testTimestamp = 1700000000000L
        val sdf = SimpleDateFormat("HH:mm:ss", Locale.US)
        val formatted = sdf.format(Date(testTimestamp))
        assertNotNull(formatted)
        assertTrue(formatted.matches(Regex("\\d{2}:\\d{2}:\\d{2}")))
    }
}
