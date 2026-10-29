package com.example.activityassignment

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.activityassignment.ui.theme.AccentYellow
import com.example.activityassignment.ui.theme.ActivityAssignmentTheme
import com.example.activityassignment.ui.theme.CardBackground
import com.example.activityassignment.ui.theme.CardBorder

/**
 * MainActivity serves as the entry point launcher activity of the application.
 * Demonstrates:
 * 1. Registering launcher activity in AndroidManifest.xml
 * 2. Creating an explicit Intent to launch SecondActivity
 * 3. Passing dynamic data payload using Intent extras
 * 4. Custom animated buttons with Jetpack Compose spring physics
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ActivityAssignmentTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var customMessage by remember { mutableStateOf("Hello from MainActivity! 🚀") }
    val scrollState = rememberScrollState()

    // Smooth gradient background
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF1E1B4B), // Deep indigo
                        Color(0xFF312E81), // Mid indigo
                        Color(0xFF0F172A)  // Deep slate
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
                            .background(Color(0xFF10B981))
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Screen 1 • MainActivity",
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            // Title
            Text(
                text = "Activity Navigation",
                fontSize = 30.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Subtitle
            Text(
                text = "Learn Intent navigation, lifecycle transitions, and Compose animation physics.",
                fontSize = 15.sp,
                color = Color.White.copy(alpha = 0.8f),
                textAlign = TextAlign.Center,
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.height(28.dp))

            // Interactive payload configuration card
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = CardBackground),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, CardBorder, RoundedCornerShape(20.dp))
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "📦 Intent Payload (Extra)",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Type a message below to pass it across to SecondActivity:",
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.7f)
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = customMessage,
                        onValueChange = { customMessage = it },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = Color(0xFF818CF8),
                            unfocusedBorderColor = Color.White.copy(alpha = 0.3f),
                            cursorColor = Color(0xFF818CF8)
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Animated Launch Button
            AnimatedButton(
                text = "Launch Second Activity ➡️",
                onClick = {
                    val intent = Intent(context, SecondActivity::class.java).apply {
                        putExtra(
                            SecondActivity.EXTRA_MESSAGE,
                            customMessage.ifBlank { "Hello from MainActivity! 🚀" }
                        )
                        putExtra(
                            SecondActivity.EXTRA_TIMESTAMP,
                            System.currentTimeMillis()
                        )
                    }
                    context.startActivity(intent)
                },
                modifier = Modifier.fillMaxWidth(0.85f)
            )

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "Uses explicit Intent(context, SecondActivity::class.java)",
                fontSize = 12.sp,
                color = Color.White.copy(alpha = 0.6f),
                textAlign = TextAlign.Center
            )
        }
    }
}

/**
 * Reusable animated button with custom spring-damping bounce,
 * dynamic color transitions, elevation shift, and entrance pop-in.
 */
@Composable
fun AnimatedButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    primaryColor: Color = Color.White,
    pressedColor: Color = AccentYellow,
    textColor: Color = Color(0xFF0F172A)
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    // Smooth spring bounce on press
    val pressScale by animateFloatAsState(
        targetValue = if (isPressed) 0.92f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "pressScale"
    )

    // Dynamic background color transition
    val containerColor by animateColorAsState(
        targetValue = if (isPressed) pressedColor else primaryColor,
        animationSpec = tween(durationMillis = 150),
        label = "buttonColor"
    )

    // Dynamic elevation shadow
    val elevation by animateDpAsState(
        targetValue = if (isPressed) 2.dp else 8.dp,
        label = "buttonElevation"
    )

    // Pop-in entrance animation
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }
    val entranceScale by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "entranceScale"
    )

    Button(
        onClick = onClick,
        interactionSource = interactionSource,
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = textColor
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = elevation,
            pressedElevation = elevation
        ),
        modifier = modifier
            .graphicsLayer {
                scaleX = pressScale * entranceScale
                scaleY = pressScale * entranceScale
                alpha = entranceScale.coerceIn(0f, 1f)
            }
            .height(56.dp)
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}