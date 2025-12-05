package hu.toliver.whacook.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Simple reusable snackbar component supporting a loading state and an error message.
 *
 * - Pass a non-null [errorMessage] to display an error snackbar.
 * - If [isLoading] is true, shows a transient snackbar with a loading indicator.
 *
 * The snackbar will auto-dismiss after [durationMs] milliseconds for loading and error.
 */
@Composable
fun MessageSnackbar(
    isLoading: Boolean,
    errorMessage: String?,
    durationMs: Long = 3000L,
    onShown: (() -> Unit)? = null
) {
    val hostState = remember { SnackbarHostState() }

    val message = when {
        isLoading -> "Loading..."
        !errorMessage.isNullOrBlank() -> errorMessage
        else -> null
    }

    LaunchedEffect(message) {
        if (!message.isNullOrBlank()) {
            onShown?.invoke()
            hostState.showSnackbar(message)
        }
    }

    Box(modifier = Modifier.fillMaxWidth()) {
        SnackbarHost(
            hostState = hostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        ) { data ->
            val backgroundColor = if (isLoading) {
                Color(0xFFFFFACD)
            } else {
                Color(0xFFFFCDD2)
            }

            Snackbar(
                modifier = Modifier
                    .padding(16.dp)
                    .border(
                        3.dp,
                        Color.Black,
                        RoundedCornerShape(50)
                    ),
                shape = RoundedCornerShape(50),
                containerColor = backgroundColor,
                contentColor = Color.Black,
            ) {
                Text(
                    text = data.visuals.message,
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
