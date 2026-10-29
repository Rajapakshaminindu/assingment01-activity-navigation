package com.example.activityassignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
 * 2. Proper handling of Activity finishing via finish() callback.
 * 3. Maintaining smooth Compose animations and theme parity.
 */
class SecondActivity : ComponentActivity() {

    companion object {
        const val EXTRA_MESSAGE = "extra_message"
        const val EXTRA_TIMESTAMP = "extra_timestamp"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val receivedMessage = intent.getStringExtra(EXTRA_MESSAGE)
            ?: "Welcome to the second activity!"
        val receivedTimestamp = intent.getLongExtra(EXTRA_TIMESTAMP, System.currentTimeMillis())

        setContent {
            ActivityAssignmentTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SecondScreen(
                        message = receivedMessage,
                        timestamp = receivedTimestamp,
                        onBack = { finish() },
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
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    val formattedTime = remember(timestamp) {
        val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        sdf.format(Date(timestamp))
    }

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

            Spacer(modifier = Modifier.height(28.dp))

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

            Spacer(modifier = Modifier.height(32.dp))

            // Return Button
            AnimatedButton(
                text = "⬅️ Go Back to Main Screen",
                onClick = onBack,
                modifier = Modifier.fillMaxWidth(0.85f),
                primaryColor = Color(0xFF38BDF8),
                pressedColor = Color(0xFF7DD3FC),
                textColor = Color(0xFF0F172A)
            )

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "Calls finish() to pop this activity off the back stack",
                fontSize = 12.sp,
                color = Color.White.copy(alpha = 0.6f),
                textAlign = TextAlign.Center
            )
        }
    }
}