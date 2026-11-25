package com.example.activityassignment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.activityassignment.ui.theme.ActivityAssignmentTheme
import com.example.activityassignment.ui.theme.CardBackground
import com.example.activityassignment.ui.theme.CardBorder
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * SecondActivity represents the secondary destination screen in the backstack.
 * Demonstrates:
 * 1. Extracting data payloads passed through Intent extras.
 * 2. Two-way communication using Android's Activity Result API (setResult).
 * 3. Proper handling of Activity finishing via finish() callback.
 * 4. Maintaining smooth Compose animations and theme parity.
 */
class SecondActivity : ComponentActivity() {

    companion object {
        const val EXTRA_MESSAGE = "extra_message"
        const val EXTRA_TIMESTAMP = "extra_timestamp"
        const val EXTRA_REPLY_MESSAGE = "extra_reply_message"
        const val EXTRA_REPLY_STATUS = "extra_reply_status"
        const val DEFAULT_MESSAGE = "Welcome to the second activity!"
        const val DEFAULT_REPLY_STATUS = "Acknowledged"

        /**
         * Factory function to create an explicit Intent for SecondActivity.
         * Encapsulates extras packaging and default fallback values.
         */
        fun createIntent(
            context: Context,
            message: String? = null,
            timestamp: Long = System.currentTimeMillis()
        ): Intent {
            return Intent(context, SecondActivity::class.java).apply {
                putExtra(
                    EXTRA_MESSAGE,
                    if (message.isNullOrBlank()) "Hello from MainActivity! 🚀" else message
                )
                putExtra(EXTRA_TIMESTAMP, timestamp)
            }
        }

        /**
         * Factory function to create result Intent returned to MainActivity.
         */
        fun createResultIntent(
            replyMessage: String?,
            status: String = DEFAULT_REPLY_STATUS,
            timestamp: Long = System.currentTimeMillis()
        ): Intent {
            return Intent().apply {
                putExtra(
                    EXTRA_REPLY_MESSAGE,
                    if (replyMessage.isNullOrBlank()) "Received your message loud and clear! 👍" else replyMessage
                )
                putExtra(EXTRA_REPLY_STATUS, status)
                putExtra(EXTRA_TIMESTAMP, timestamp)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val receivedMessage = intent.getStringExtra(EXTRA_MESSAGE)
            ?: DEFAULT_MESSAGE
        val receivedTimestamp = intent.getLongExtra(EXTRA_TIMESTAMP, System.currentTimeMillis())

        setContent {
            ActivityAssignmentTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SecondScreen(
                        message = receivedMessage,
                        timestamp = receivedTimestamp,
                        onSendReply = { replyText, status ->
                            val resultIntent = createResultIntent(replyText, status)
                            setResult(Activity.RESULT_OK, resultIntent)
                            finish()
                        },
                        onBack = {
                            setResult(Activity.RESULT_CANCELED)
                            finish()
                        },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun SecondScreen(
    message: String,
    timestamp: Long,
    onSendReply: (String, String) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    val formattedTime = remember(timestamp) {
        val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        sdf.format(Date(timestamp))
    }

    var replyMessage by remember { mutableStateOf("Message received and acknowledged!") }
    var selectedStatus by remember { mutableStateOf("Acknowledged ✅") }

    val statusOptions = listOf(
        "Acknowledged ✅",
        "Completed 🎉",
        "Need Details ❓"
    )

    // Complementary inverted gradient background
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF0F172A), // Deep slate
                        Color(0xFF312E81), // Mid indigo
                        Color(0xFF1E1B4B)  // Deep indigo
                    )
                )
            )
            .then(modifier)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 24.dp, vertical = 32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header Pill Badge
            Surface(
                shape = CircleShape,
                color = Color.White.copy(alpha = 0.15f),
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF38BDF8))
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Screen 2 • SecondActivity",
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            // Title
            Text(
                text = "Welcome to Screen 2! ✌️",
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Successfully navigated using Android Intent system.",
                fontSize = 15.sp,
                color = Color.White.copy(alpha = 0.8f),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Received Intent Payload Card
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = CardBackground),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, CardBorder, RoundedCornerShape(20.dp))
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "📥 Received Intent Payload",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF38BDF8)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "\"$message\"",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        lineHeight = 24.sp
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Received at:",
                            fontSize = 12.sp,
                            color = Color.White.copy(alpha = 0.6f)
                        )
                        Text(
                            text = formattedTime,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            fontFamily = FontFamily.Monospace,
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Back Stack Status:",
                            fontSize = 12.sp,
                            color = Color.White.copy(alpha = 0.6f)
                        )
                        Text(
                            text = "Top of Stack",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF34D399)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Reply Response Card (Two-way communication)
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = CardBackground),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, CardBorder, RoundedCornerShape(20.dp))
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "📤 Send Response Back (Activity Result)",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFFFBBF24)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Select a response status and message to return to MainActivity:",
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.7f)
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    // Status Chips
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        statusOptions.forEach { status ->
                            val isSelected = status == selectedStatus
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = if (isSelected) Color(0xFF38BDF8) else Color.White.copy(alpha = 0.1f),
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { selectedStatus = status }
                            ) {
                                Text(
                                    text = status,
                                    fontSize = 11.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    color = if (isSelected) Color(0xFF0F172A) else Color.White,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = replyMessage,
                        onValueChange = { replyMessage = it },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = Color(0xFFFBBF24),
                            unfocusedBorderColor = Color.White.copy(alpha = 0.3f),
                            cursorColor = Color(0xFFFBBF24)
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Send Reply Button
            AnimatedButton(
                text = "Send Reply & Return 🚀",
                onClick = { onSendReply(replyMessage, selectedStatus) },
                modifier = Modifier.fillMaxWidth(0.85f),
                primaryColor = Color(0xFF38BDF8),
                pressedColor = Color(0xFF7DD3FC),
                textColor = Color(0xFF0F172A)
            )

            Spacer(modifier = Modifier.height(10.dp))

            TextButton(onClick = onBack) {
                Text(
                    text = "Cancel & Return Without Reply",
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 13.sp
                )
            }
        }
    }
}
