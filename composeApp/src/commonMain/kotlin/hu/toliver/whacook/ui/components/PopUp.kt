package hu.toliver.whacook.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hu.toliver.whacook.ui.theme.LightColors

@Composable
fun PopUp(
    headerText: String,
    bodyText: String,
    buttonText: String,
    dismiss: Boolean = false,
    dismissText: String = "",
    onConfirm: () -> Unit = {},
    onDismiss: () -> Unit = {}
) {
    val color = LightColors
    Card(
        modifier = Modifier
            .responsiveWidth(400.dp)
            .border(3.dp, color.stroke, RoundedCornerShape(24.dp)),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = color.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        )
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Header(headerText)

            Spacer(modifier = Modifier.height(16.dp))

            BodyText(
                text = bodyText,
                fontSize = 18.sp,
                color = color.primaryText
            )

            Spacer(modifier = Modifier.height(32.dp))

            FlowRow(
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                PButton(
                    text = buttonText,
                    onClick = onConfirm
                )
                if (dismiss) {
                    Spacer(modifier = Modifier.width(16.dp))
                    PButton(
                        text = dismissText,
                        onClick = onDismiss,
                        backgroundColor = color.secondaryButton
                    )
                }
            }
        }
    }
}

@Composable
fun PopUpOverlay(
    headerText: String,
    bodyText: String,
    dismiss: Boolean = false,
    buttonText: String,
    dismissText: String = "",
    onConfirm: () -> Unit = {},
    onDismiss: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onDismiss() },
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier.clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {}
        ) {
            PopUp(
                headerText = headerText,
                bodyText = bodyText,
                dismiss = dismiss,
                dismissText = dismissText,
                buttonText = buttonText,
                onConfirm = {
                    onConfirm()
                },
                onDismiss = {
                    onDismiss()
                }
            )
        }
    }
}