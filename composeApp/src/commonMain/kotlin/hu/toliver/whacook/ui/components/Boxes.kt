package hu.toliver.whacook.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hu.toliver.whacook.domain.model.Duration
import hu.toliver.whacook.ui.theme.LightColors
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import whacook.composeapp.generated.resources.Res
import whacook.composeapp.generated.resources.down
import whacook.composeapp.generated.resources.edit
import whacook.composeapp.generated.resources.fryingpan
import whacook.composeapp.generated.resources.home
import whacook.composeapp.generated.resources.more
import whacook.composeapp.generated.resources.squaredmenu

fun Modifier.responsiveWidth(maxWidth: Dp): Modifier = this
    .fillMaxWidth()
    .wrapContentWidth(Alignment.CenterHorizontally)
    .layout { measurable, constraints ->
    val maxPx = maxWidth.roundToPx()
    val fractionPx = if (constraints.hasBoundedWidth) (constraints.maxWidth * 0.85f).toInt() else maxPx
    val targetWidth = fractionPx.coerceAtMost(maxPx)
    val finalWidth = targetWidth.coerceIn(constraints.minWidth, constraints.maxWidth)

    val placeable = measurable.measure(
        constraints.copy(
            minWidth = finalWidth,
            maxWidth = finalWidth
        )
    )
    layout(placeable.width, placeable.height) {
        placeable.placeRelative(0, 0)
    }
}

@Composable
fun DurationChooser(
    duration: Duration,
    onValueChange: (Duration) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val color = LightColors
    Row(
        modifier = modifier.responsiveWidth(350.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = if (duration.length == 0.0) "" else duration.length.toString().removeSuffix(".0"),
            onValueChange = {
                val newText = it.filter { char -> char.isDigit() || char == '.' }
                val newLength = newText.toDoubleOrNull() ?: 0.0
                onValueChange(duration.copy(length = newLength))
            },
            modifier = Modifier
                .weight(0.4f)
                .height(64.dp),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = color.surface,
                unfocusedContainerColor = color.surface,
                focusedBorderColor = color.primaryText,
                unfocusedBorderColor = color.secondaryStroke,
                cursorColor = color.primaryText,
                focusedTextColor = color.primaryText,
                unfocusedTextColor = color.primaryText,
            ),
            textStyle = androidx.compose.ui.text.TextStyle(
                fontSize = 24.sp,
                color = color.primaryText
            ),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
        )

        var expanded by remember { mutableStateOf(false) }
        val units = listOf("minutes", "hours")

        Box(
            modifier = Modifier
                .weight(0.6f)
                .height(64.dp)
                .border(1.dp, color.secondaryStroke, RoundedCornerShape(16.dp))
                .clip(RoundedCornerShape(16.dp))
                .background(color.surface)
                .clickable { expanded = true }
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = duration.unit,
                    fontSize = 20.sp,
                    color = color.primaryText
                )
                Image(
                    painter = painterResource(Res.drawable.down),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.background(color.surface)
            ) {
                units.forEach { unit ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = unit,
                                fontSize = 18.sp,
                                color = color.primaryText
                            )
                        },
                        onClick = {
                            onValueChange(duration.copy(unit = unit))
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun MenuElement(
    text: String,
    icon: DrawableResource = Res.drawable.squaredmenu,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    val color = LightColors
    Row(
        modifier = modifier
            .responsiveWidth(600.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(color.surface)
            .border(1.dp, color.secondaryStroke, RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(horizontal = 24.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Image(
                painter = painterResource(icon),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = text,
                fontSize = 18.sp,
                color = color.primaryText
            )
        }
        Image(
            painter = painterResource(Res.drawable.more),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
fun NavBar(
    onMenuClick: () -> Unit = {},
    onHomeClick: () -> Unit = {},
    onEditClick: () -> Unit = {}
) {
    val color = LightColors
    Box(
        modifier = Modifier
            .responsiveWidth(350.dp)
            .height(80.dp)
            .border(1.dp, Color.Black, RoundedCornerShape(50.dp))
            .clip(RoundedCornerShape(50.dp))
            .background(color.surface)
    ) {
        BoxWithConstraints(
            modifier = Modifier.fillMaxSize()
        ) {
            var selectedTab by remember { mutableStateOf(1) }

            val width = maxWidth
            val itemWidth = width / 3
            val indicatorOffset by animateDpAsState(
                targetValue = when (selectedTab) {
                    0 -> 0.dp
                    1 -> itemWidth
                    2 -> itemWidth * 2
                    else -> itemWidth
                }
            )

            Box(
                modifier = Modifier
                    .offset(x = indicatorOffset)
                    .width(itemWidth)
                    .fillMaxHeight()
                    .padding(12.dp)
                    .background(color.secondaryButton, CircleShape)
            )

            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) { selectedTab = 0; onMenuClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(Res.drawable.squaredmenu),
                        contentDescription = "Menu",
                        modifier = Modifier.size(28.dp)
                    )
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) { selectedTab = 1; onHomeClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(Res.drawable.home),
                        contentDescription = "Home",
                        modifier = Modifier.size(28.dp)
                    )
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) { selectedTab = 2; onEditClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(Res.drawable.edit),
                        contentDescription = "Edit",
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        }
    }
}

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

@Composable
fun RecipeCard(
    title: String,
    time: String,
    ingredientsCount: Int,
    date: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    val color = LightColors
    Card(
        modifier = modifier
            .responsiveWidth(800.dp)
            .border(1.dp, color.secondaryStroke, RoundedCornerShape(16.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = color.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            BodyTextHeader(
                title,
                color = color.primaryText,
            )
            BodyText(
                "Time to make: $time",
                color = color.primaryText
            )
            BodyText(
                "Num of ingredients: $ingredientsCount things",
                color = color.primaryText
            )
            BodyTextSmall(
                date,
                color = color.secondaryText
            )
        }
    }
}

@Composable
fun SearchCard(
    modifier: Modifier = Modifier,
    onSearchClick: () -> Unit = {},
    onDescribeClick: () -> Unit = {}
) {
    val color = LightColors
    Card(
        modifier = modifier
            .responsiveWidth(800.dp)
            .shadow(
                elevation = 10.dp,
                shape = RoundedCornerShape(24.dp),
                spotColor = color.primary,
                ambientColor = color.primary
            ),
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
                .padding(vertical = 24.dp, horizontal = 16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text(
                text = "What are we cooking today?",
                fontSize = 22.sp,
                color = color.primaryText,
                modifier = Modifier.fillMaxWidth(0.85f)
            )

            TypeBar(
                text = "Type in your ingredients!",
                onClick = onSearchClick
            )

            BodyTextUnderline(
                text = "...or just describe your food!",
                onClick = onDescribeClick
            )
        }
    }
}

@Composable
fun TextBox(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    onDone: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val color = LightColors
    OutlinedTextField(
        value = value,
        onValueChange = { onValueChange(it) },
        placeholder = {
            Text(
                text = placeholder,
                color = color.secondaryText
            )
        },
        modifier = modifier
            .responsiveWidth(600.dp)
            .height(150.dp)
            .onPreviewKeyEvent {
                if (it.key == Key.Enter && it.type == KeyEventType.KeyDown) {
                    onDone()
                    true
                } else {
                    false
                }
            },
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = color.surface,
            unfocusedContainerColor = color.surface,
            focusedBorderColor = color.primaryText,
            unfocusedBorderColor = color.secondaryStroke,
            cursorColor = color.primaryText,
            focusedTextColor = color.primaryText,
            unfocusedTextColor = color.primaryText,
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Go),
        keyboardActions = KeyboardActions(
            onGo = { onDone() }
        )
    )
}

@Composable
fun TypeBar(
    text: String,
    icon: DrawableResource = Res.drawable.fryingpan,
    onClick: () -> Unit = {},
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(0.85f)
            .clip(CircleShape)
            .border(1.dp, Color.LightGray, CircleShape)
            .clickable { onClick() }
            .padding(horizontal = 24.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = text,
            fontSize = 18.sp,
            color = LightColors.primaryText
        )
        Image(
            painter = painterResource(icon),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
    }
}