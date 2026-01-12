package hu.toliver.whacook.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.*
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hu.toliver.whacook.domain.model.Duration
import hu.toliver.whacook.ui.theme.LightColors
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import whacook.composeapp.generated.resources.*

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
fun IngredientEditCard(
    name: String,
    onNameChange: (String) -> Unit = {},
    amount: Double,
    onAmountChange: (Double) -> Unit = {},
    unit: String,
    onUnitChange: (String) -> Unit = {},
    onDelete: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val color = LightColors
    var amountText by remember { mutableStateOf(amount.toString().removeSuffix(".0")) }

    LaunchedEffect(amount) {
        if (amountText.toDoubleOrNull() != amount) {
            amountText = amount.toString().removeSuffix(".0")
        }
    }

    Column(
        modifier = modifier
            .responsiveWidth(600.dp, 1.0f)
            .border(1.dp, color.secondaryStroke, RoundedCornerShape(16.dp))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = onNameChange,
            modifier = Modifier
                .fillMaxWidth()
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
            singleLine = true
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = amountText,
                onValueChange = {
                    if (it.count { char -> char == '.' } <= 1 && it.all { char -> char.isDigit() || char == '.' }) {
                        amountText = it
                        val doubleValue = it.toDoubleOrNull()
                        if (doubleValue != null) {
                            onAmountChange(doubleValue)
                        } else if (it.isEmpty()) {
                            onAmountChange(0.0)
                        }
                    }
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

            OutlinedTextField(
                value = unit,
                onValueChange = onUnitChange,
                modifier = Modifier
                    .weight(0.6f)
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
                singleLine = true
            )

            IconButton(
                onClick = { onDelete() }
            ) {
                Icon(
                    painter = painterResource(Res.drawable.trashcan),
                    contentDescription = null,
                    modifier = Modifier.size(32.dp)
                )
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
    /* onDescribeClick: () -> Unit = {}*/
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

            /* I will comment this out for now, as the feature is not ready yet
            BodyTextUnderline(
                text = "...or just describe your food!",
                onClick = onDescribeClick
            )
             */
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