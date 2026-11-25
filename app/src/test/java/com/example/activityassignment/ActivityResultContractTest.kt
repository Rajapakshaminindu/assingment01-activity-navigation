package com.example.activityassignment

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

/**
 * Unit tests verifying Activity Result contract constants, factory method behavior,
 * default fallback responses, and two-way payload encapsulation.
 */
class ActivityResultContractTest {

    @Test
    fun replyKeys_areProperlyDefined() {
        assertEquals("extra_reply_message", SecondActivity.EXTRA_REPLY_MESSAGE)
        assertEquals("extra_reply_status", SecondActivity.EXTRA_REPLY_STATUS)
    }

    @Test
    fun defaultReplyStatus_isAcknowledged() {
        assertEquals("Acknowledged", SecondActivity.DEFAULT_REPLY_STATUS)
    }

    @Test
    fun createResultIntent_setsPayloadValues() {
        val testReply = "Task finished successfully"
        val testStatus = "Completed 🎉"
        val testTimestamp = 1700000000000L

        val intent = SecondActivity.createResultIntent(
            replyMessage = testReply,
            status = testStatus,
            timestamp = testTimestamp
        )

        assertNotNull(intent)
        assertEquals(testReply, intent.getStringExtra(SecondActivity.EXTRA_REPLY_MESSAGE))
        assertEquals(testStatus, intent.getStringExtra(SecondActivity.EXTRA_REPLY_STATUS))
        assertEquals(testTimestamp, intent.getLongExtra(SecondActivity.EXTRA_TIMESTAMP, 0L))
    }

    @Test
    fun createResultIntent_usesDefaultWhenMessageIsBlank() {
        val intentBlank = SecondActivity.createResultIntent(replyMessage = "")
        val intentNull = SecondActivity.createResultIntent(replyMessage = null)

        val expectedDefault = "Received your message loud and clear! 👍"
        assertEquals(expectedDefault, intentBlank.getStringExtra(SecondActivity.EXTRA_REPLY_MESSAGE))
        assertEquals(expectedDefault, intentNull.getStringExtra(SecondActivity.EXTRA_REPLY_MESSAGE))
    }

    @Test
    fun createResultIntent_defaultsStatusWhenOmitted() {
        val intent = SecondActivity.createResultIntent(replyMessage = "Hello back")
        assertEquals(SecondActivity.DEFAULT_REPLY_STATUS, intent.getStringExtra(SecondActivity.EXTRA_REPLY_STATUS))
    }
}
